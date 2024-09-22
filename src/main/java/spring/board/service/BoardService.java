package spring.board.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import spring.board.model.dto.request.BoardEditRequestDto;
import spring.board.model.dto.request.BoardSaveRequestDto;
import spring.board.model.dto.response.BoardResponseDto;
import spring.board.model.entity.Board;
import spring.board.model.entity.BoardStatus;
import spring.board.model.entity.QBoard;
import spring.board.model.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final JPAQueryFactory queryFactory;

    @Transactional
    public BoardResponseDto save(BoardSaveRequestDto requestDto) {
        if (StringUtils.isBlank(requestDto.getContent())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글 내용은 필수입니다.");
        }
        else if (StringUtils.isBlank(requestDto.getTitle())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글 제목은 필수입니다.");
        }

        Board savedPost = boardRepository.save(requestDto.toEntity());
        return new BoardResponseDto(savedPost);
    }

    // 수정
    @Transactional
    public BoardResponseDto edit(BoardEditRequestDto requestDto) {
        Board posts = boardRepository.findById(requestDto.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 없습니다. "));
        if (posts.getStatus()== BoardStatus.DELETED || posts.getStatus() == BoardStatus.INACTIVE)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글은 수정할 수 없습니다.");
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return new BoardResponseDto(posts);
    }

    // 조회, 커서 기반 페이징
    @Transactional(readOnly = true)
    public Slice<BoardResponseDto> findPage(Long lastBoardId, int size) {
        QBoard board = QBoard.board;

        // 커서 기반으로 페이징 처리
        List<BoardResponseDto> results = queryFactory
                .select(Projections.constructor(BoardResponseDto.class,
                        board.id,
                        board.title,
                        board.content,
                        board.createdDate
                ))
                .from(board)
                .where( board.status.eq(BoardStatus.ACTIVE), // 삭제되지 않은 게시물만 조회하도록 설정
                        lastBoardId != null ? board.id.lt(lastBoardId) : null)  // lastBoardId 이후의 게시글을 가져오기
                .orderBy(board.createdDate.desc())  // 최신순 정렬
                .limit(size + 1)  // 요청 크기보다 1개 더 조회 (다음 페이지 여부 확인용)
                .fetch();

        // 다음 페이지가 있는지 확인
        boolean hasNext = false;
        if (results.size() > size) {
            results.remove(size);  // 마지막 데이터는 제외 (다음 페이지 존재 여부만 확인)
            hasNext = true;
        }
        // 결과 리스트, 현재 페이지(0), 요청 크기, 다음 페이지 존재 여부 포함한 Slice 객체 반환
        return new SliceImpl<>(results, PageRequest.of(0, size), hasNext);
    }


    @Transactional
    public BoardResponseDto deleteById(Long id){
        Board post = boardRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 없습니다. "));
        if (post.getStatus()!=BoardStatus.DELETED){
            post.delete(id);
            // 댓글도 소프트 삭제 처리
            post.getComments().forEach(comment -> {
                comment.delete();
            });
        }
        return new BoardResponseDto(post);
    }


}

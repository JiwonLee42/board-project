package spring.board.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import spring.board.model.dto.request.CommentEditRequestDto;
import spring.board.model.dto.request.CommentSaveRequestDto;
import spring.board.model.dto.response.CommentResponseDto;
import spring.board.model.entity.*;
import spring.board.model.repository.BoardRepository;
import spring.board.model.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Getter
public class CommentService  {

    private CommentRepository commentRepository;

    private BoardRepository boardRepository;
    private JPAQueryFactory queryFactory;

    @Transactional
    public CommentResponseDto save(CommentSaveRequestDto requestDto) {
        Board board = boardRepository.findById(requestDto.getPostId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 없습니다. "));
        if (StringUtils.isBlank(requestDto.getContent())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글 내용은 필수입니다.");
        }
        Comment savedComment = commentRepository.save(requestDto.toEntity(board));
        return new CommentResponseDto(savedComment);
    }

    // 수정
    @Transactional
    public CommentResponseDto edit(CommentEditRequestDto requestDto) {
        Comment comment = commentRepository.findById(requestDto.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글이 없습니다. "));
        if (comment.getStatus()== CommentStatus.DELETED || comment.getStatus() == CommentStatus.INACTIVE)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글은 수정할 수 없습니다.");
        comment.update(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto deleteById(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글이 없습니다. "));
        if (comment.getStatus()!= CommentStatus.DELETED)
            comment.delete();
        return new CommentResponseDto(comment);
    }

    // 댓글 조회 ( deleted 아닌것만 조회하기 )
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentList(Long postId) {
        QComment comment = QComment.comment;

        List<Comment> comments = queryFactory
                .selectFrom(comment)
                .where(comment.board.id.eq(postId)
                        .and(comment.status.eq(CommentStatus.ACTIVE)))
                .orderBy(comment.createdDate.asc())
                .fetch();
        return comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }



}

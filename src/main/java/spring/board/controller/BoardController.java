package spring.board.controller;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.board.model.dto.request.BoardEditRequestDto;
import spring.board.model.dto.request.BoardSaveRequestDto;
import spring.board.model.dto.response.BoardResponseDto;
import spring.board.service.BoardService;

@RestController
@NoArgsConstructor
public class BoardController {

    private BoardService boardService;

    @PostMapping("/board/write")
    public ResponseEntity<BoardResponseDto> save(@RequestBody BoardSaveRequestDto requestDto) {
        BoardResponseDto responseDto = boardService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/board/edit/{postId}")
    public ResponseEntity<BoardResponseDto> edit(@RequestBody BoardEditRequestDto requestDto) {
        BoardResponseDto responseDto = boardService.edit(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/board/lists")
    public Slice<BoardResponseDto> findPage(
            @RequestParam(required = false) Long lastBoardId,
            @RequestParam(defaultValue = "30") int size) {
        return boardService.findPage(lastBoardId, size);
    }

    @PatchMapping("/board/delete/{postId}")
    public ResponseEntity<BoardResponseDto> delete(@PathVariable Long postId) {
        BoardResponseDto responseDto = boardService.deleteById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


}

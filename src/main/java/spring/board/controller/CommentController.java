package spring.board.controller;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.board.model.dto.request.CommentEditRequestDto;
import spring.board.model.dto.request.CommentSaveRequestDto;
import spring.board.model.dto.response.CommentResponseDto;
import spring.board.service.CommentService;

import java.util.List;

@RestController
@NoArgsConstructor
@Getter
public class CommentController {

    private CommentService commentService;

    @PostMapping("/comment/write")
    public ResponseEntity<CommentResponseDto> save(@RequestBody CommentSaveRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/comment/edit")
    public ResponseEntity<CommentResponseDto> edit(@RequestBody CommentEditRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.edit(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping("/comment/delete/{commentId}")
    public ResponseEntity<CommentResponseDto> delete(@PathVariable Long postId) {
        CommentResponseDto responseDto = commentService.deleteById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/comment/view/{postId}")
    public ResponseEntity<List<CommentResponseDto>> viewComment(@PathVariable Long postId) {
        List<CommentResponseDto> responseDtos = commentService.getCommentList(postId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }
}

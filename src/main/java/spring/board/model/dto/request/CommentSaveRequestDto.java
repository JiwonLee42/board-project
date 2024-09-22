package spring.board.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.model.entity.Board;
import spring.board.model.entity.Comment;


@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private String content;
    private Long postId;

    @Builder
    public CommentSaveRequestDto(String content){
        this.content = content;
    }
    public Comment toEntity(Board board) {
        return Comment.builder()
                .content(content)
                .board(board)
                .build();
    }
}

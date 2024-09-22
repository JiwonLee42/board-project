package spring.board.model.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.model.entity.Board;
import spring.board.model.entity.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    Long id;
    String content;
    LocalDateTime CreatedDate;

    @Builder
    public CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.CreatedDate = entity.getCreatedDate();
    }
}

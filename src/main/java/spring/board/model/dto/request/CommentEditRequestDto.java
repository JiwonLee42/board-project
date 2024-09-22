package spring.board.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentEditRequestDto {

    Long id;
    String content;
}

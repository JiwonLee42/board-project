package spring.board.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.model.entity.Board;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {

    private String title;
    private String content;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }
}

package spring.board.model.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.model.entity.Board;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;

    private LocalDateTime CreatedDate;

    @Builder
    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.CreatedDate = entity.getCreatedDate();
    }
}

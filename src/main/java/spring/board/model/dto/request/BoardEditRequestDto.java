package spring.board.model.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardEditRequestDto {

    private Long id;
    private String title;
    private String content;
}

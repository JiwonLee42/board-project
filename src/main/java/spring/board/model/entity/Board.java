package spring.board.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.board.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    @Lob
    String content;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    // 댓글 리스트
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(String title,String content) {
        this.title = title;
        this.content = content;
        this.status = BoardStatus.ACTIVE;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete(Long id){
        this.status = BoardStatus.DELETED;
        for (Comment comment : comments) {
            comment.delete();
        }
    }


}

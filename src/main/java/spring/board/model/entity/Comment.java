package spring.board.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.board.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Lob
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Board board;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    @Builder
    public Comment(String content, Board board) {
        this.content = content;
        this.status = CommentStatus.ACTIVE;
        this.board = board;
    }

    public void update(String content) {
        this.content = content;
    }

    public void delete(){
        this.status = CommentStatus.DELETED;
    }

}

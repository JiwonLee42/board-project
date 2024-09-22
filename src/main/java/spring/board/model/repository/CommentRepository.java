package spring.board.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.board.model.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}

package spring.board.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.board.model.entity.Board;

public interface BoardRepository extends JpaRepository<Board,Long> {

}

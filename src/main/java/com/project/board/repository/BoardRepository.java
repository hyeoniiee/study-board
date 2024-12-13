package com.project.board.repository;

import com.project.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b, w from Board b left join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select b, w " +
            "from Board b " +
            "left join b.writer w " +
            "group by b")
    List<Object[]> getAllBoardsWithWriters();

    @Query(value = "SELECT b.bno, b.title, m.name " +
            "FROM tbl_board b LEFT JOIN tbl_member m ON b.writer_email = m.email",
            nativeQuery = true)
    List<Object[]> getNativeBoardsWithWriters();
}

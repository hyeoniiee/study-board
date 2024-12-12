package com.project.board.repository;

import com.project.board.entity.Board;
import com.project.board.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    @Test
    public void setup() {
        Member member = Member.builder()
                .email("user@abcs.com")
                .name("user")
                .password("1111")
                .build();
        memberRepository.save(member);

        Board board = Board.builder()
                .title("title")
                .content("content")
                .writer(member)
                .build();
        boardRepository.save(board);
    }

    @Test
    public void testInsert() {
        // 새로운 게시글 생성 및 저장
        Member member = memberRepository.findById("user@abcc.com").orElseThrow();
        Board newBoard = Board.builder()
                .title("title111")
                .content("content111")
                .writer(member)
                .build();

        Board savedBoard = boardRepository.save(newBoard);

        assertThat(savedBoard).isNotNull();
        assertThat(savedBoard.getBno()).isNotNull();
    }

    @Test
    public void testUpdate() {
        Long bnoToUpdate = 1L;
        Optional<Board> boardOptional = boardRepository.findById(bnoToUpdate);

        boardOptional.ifPresent(board -> {
            board.changeTitle("title222");
            board.changeContent("content222");
            boardRepository.save(board);
        });

        Optional<Board> updatedBoardOptional = boardRepository.findById(bnoToUpdate);
        updatedBoardOptional.ifPresent(updatedBoard -> {
            assertThat(updatedBoard.getTitle()).isEqualTo("title111");
            assertThat(updatedBoard.getContent()).isEqualTo("content111");
            System.out.println("update board: " + updatedBoard);
        });
    }

    @Test
    public void testDelete() {
        Long bnoToDelete = 1L;
        Optional<Board> boardOptional = boardRepository.findById(bnoToDelete);
        boardRepository.deleteById(bnoToDelete);
        Optional<Board> deletedBoard = boardRepository.findById(bnoToDelete);
        assertThat(deletedBoard).isNotPresent();
    }
}

package com.project.board.repository;

import com.project.board.entity.Board;
import com.project.board.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach // 테스트 실행 전에 미리 데이터를 준비하는 메서드
    @Test
    public void setup() {
        Member member = Member.builder()
                .email("user@abcs.com")
                .name("user")
                .password("1111")
                .build();
        memberRepository.save(member); // 데이터베이스에 Member 저장

        Board board = Board.builder()
                .title("title")
                .content("content")
                .writer(member)
                .build();
        boardRepository.save(board); // 데이터베이스에 Board 저장
    } // 테스트용 Member, Board 생성

    @Test
    public void testInsert() {
        Member member = memberRepository.findById("user@abcc.com").orElseThrow(); // 존재하는 회원을 조회
        Board newBoard = Board.builder() // 새로운 게시글 생성
                .title("title111").content("content111").writer(member).build();
        Board savedBoard = boardRepository.save(newBoard);
        // 게시글이 정상적으로 저장되었는지 검증
        assertThat(savedBoard).isNotNull(); // 저장된 객체가 null이 아님
        assertThat(savedBoard.getBno()).isNotNull(); // 게시글 ID가 생성되었는지 확인
    } // 새로운 게시글 생성 및 저장

    @Test
    public void testUpdate() {
        Long bnoToUpdate = 1L; // 수정할 게시글의 ID
        Optional<Board> boardOptional = boardRepository.findById(bnoToUpdate); // 해당 ID의 게시글을 조회
        boardOptional.ifPresent(board -> {
            board.changeTitle("title222");
            board.changeContent("content222");
            boardRepository.save(board);
        }); // 게시글이 존재하면 제목과 내용을 수정

        Optional<Board> updatedBoardOptional = boardRepository.findById(bnoToUpdate);
        updatedBoardOptional.ifPresent(updatedBoard -> {
            assertThat(updatedBoard.getTitle()).isEqualTo("title222");
            assertThat(updatedBoard.getContent()).isEqualTo("content222");
            System.out.println("update board: " + updatedBoard);
        }); // 수정된 게시글을 다시 조회
    }

    @Test
    public void testDelete() {
        Long bnoToDelete = 1L; // 삭제할 게시글의 ID
        Optional<Board> boardOptional = boardRepository.findById(bnoToDelete); // 해당 ID의 게시글을 조회 (존재 여부 확인)
        boardRepository.deleteById(bnoToDelete);
        // 삭제 후 다시 조회하여 게시글이 삭제되었는지 확인
        Optional<Board> deletedBoard = boardRepository.findById(bnoToDelete);
        assertThat(deletedBoard).isNotPresent(); // 삭제된 게시글은 존재하지 않아야 함
    }

    @Test
    public void testPaging() {
        // 페이징 처리 (2페이지, 페이지당 5개 게시글)
        PageRequest pageRequest = PageRequest.of(1, 5, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageRequest);

        // 결과 검증
        assertThat(result.getContent().size()).isEqualTo(5); // 페이지당 게시글 수 확인
        assertThat(result.getTotalPages()).isGreaterThanOrEqualTo(2); // 총 페이지 수 확인
        result.getContent().forEach(board -> System.out.println(board));
    }

    @Test
    public void testSorting() {
        // 정렬 테스트 (작성자를 기준으로 오름차순 정렬)
        List<Board> boards = boardRepository.findAll(Sort.by(Sort.Order.asc("writer.name")));
        assertThat(boards).isNotEmpty();
        boards.forEach(board -> System.out.println(board));
    }

    @Test
    public void testCustomQuery() {
        // JPQL 쿼리 테스트: 특정 키워드가 제목에 포함된 게시글 검색
        List<Object[]> boardsWithWriters = boardRepository.getAllBoardsWithWriters();
        assertThat(boardsWithWriters).isNotEmpty();
        boardsWithWriters.forEach(objects -> {
            System.out.println("Board: " + objects[0]);
            System.out.println("Writer: " + objects[1]);
            System.out.println("Reply Count: " + objects[2]);
        });
    }

    @Test
    public void testNativeQuery() {
        // 네이티브 SQL 쿼리 테스트
        List<Object[]> result = boardRepository.getNativeBoardsWithWriters();
        assertThat(result).isNotEmpty();
        result.forEach(objects -> {
            System.out.println("Board ID: " + objects[0]);
            System.out.println("Title: " + objects[1]);
            System.out.println("Writer: " + objects[2]);
        });
    }
}




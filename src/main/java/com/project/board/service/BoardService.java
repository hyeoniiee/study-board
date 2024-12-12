package com.project.board.service;

import com.project.board.dto.BoardDTO;
import com.project.board.entity.Board;
import com.project.board.entity.Member;

public interface BoardService {

    // 게시물 등록
    Long register(BoardDTO dto);

    // 게시물 조회
    BoardDTO get(Long bno);

    // 게시물 삭제
    void delete(Long bno);

    default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
            .bno(dto.getBno())
            .title(dto.getTitle())
            .content(dto.getContent())
            .writer(member)
            .build();
        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
            .bno(board.getBno())
            .title(board.getTitle())
            .content(board.getContent())
            .regDate(board.getRegDate())
            .modDate(board.getModDate())
            .writerEmail(member.getEmail())
            .writerName(member.getName())
            .replyCount(replyCount.intValue()) //int로 처리하도록
            .build();

        return boardDTO;
    }
}
package com.project.board.service;

import com.project.board.dto.BoardDTO;
import com.project.board.entity.Board;
import com.project.board.entity.Member;
import com.project.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository;

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);
        Board board  = dtoToEntity(dto);
        repository.save(board);
        return board.getBno();
    }

    @Override
    public BoardDTO get(Long bno) {
        Object[] result = (Object[]) repository.getBoardWithWriter(bno);

        Board board = (Board) result[0];
        Member member = (Member) result[1];
        Long replyCount = 0L;

        return entityToDTO(board, member, replyCount);
    }

    @Override
    public void delete(Long bno) {
         repository.deleteById(bno);
    }

}

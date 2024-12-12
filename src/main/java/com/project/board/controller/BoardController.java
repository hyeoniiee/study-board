package com.project.board.controller;

import com.project.board.dto.BoardDTO;
import com.project.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(Model model) {
        log.info("board list...");
    }

    @GetMapping("/register")
    public void register(){
        log.info("regiser get...");
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute BoardDTO dto, RedirectAttributes redirectAttributes) {
        log.info("board: {}", dto);
        // 게시물 등록 후 생성된 ID 반환
        Long bno = boardService.register(dto);
        // 등록 성공 메시지 추가
        redirectAttributes.addFlashAttribute("msg", "Board ID: " + bno);
        return "redirect:/board/list";
    }

    @PostMapping("/delete/{bno}")
    public String delete(@PathVariable Long bno, RedirectAttributes redirectAttributes) {
        log.info("ID: {}", bno);
        boardService.delete(bno);
        redirectAttributes.addFlashAttribute("msg", "Board delete");
        return "redirect:/board/list";
    }
}

package com.project.board.service;

import com.project.board.entity.Member;
import com.project.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member getMember(String email) {
        log.info("Member email: " + email);
        return memberRepository.findById(email).orElseThrow(() -> new RuntimeException("Member not found"));
    }

}

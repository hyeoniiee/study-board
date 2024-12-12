package com.project.board.service;

import com.project.board.entity.Member;

public interface MemberService {
    // 멤버 조회 (email 기준)
    Member getMember(String email);
}

package com.project.board.repository;

import com.project.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository
    extends JpaRepository<Member, String>, QuerydslPredicateExecutor<Member> {
  // 55:30
}

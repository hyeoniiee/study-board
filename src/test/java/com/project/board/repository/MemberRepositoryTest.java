package com.project.board.repository;

import com.project.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.stream.IntStream;

@SpringBootTest
@EnableJpaAuditing
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                .email("user" + i + "@abcs.com")
                .password("1111")
                .name("user" + i)
                .build();
            memberRepository.save(member);
        });
    }
}
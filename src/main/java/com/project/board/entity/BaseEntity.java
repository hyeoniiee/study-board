package com.project.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
//EntityListener 란 Entity 가 삽입, 삭제, 수정, 조회 등의 작업을 할 때 전, 후에 어떠한 작업을 하기 위해 이벤트 처리를 위한 어노테이션이다
//@MappedSuperclass 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공한다.
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
public class BaseEntity {
  @CreatedDate
  @Column(name = "regdate", updatable = false)
  private LocalDateTime regDate; // 등록날짜

  @LastModifiedDate
  @Column(name ="moddate")
  private LocalDateTime modDate; // 수정날짜
}

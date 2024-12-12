package com.project.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
  private Long bno;

  private String title;

  private String content;

  private String writerEmail; //작성자의 이메일(id)

  private String writerName; //작성자의 이름

  private LocalDateTime regDate; // 등록날짜

  private LocalDateTime modDate; // 수정날짜

  private int replyCount; //해당 게시글의 댓글 수
}

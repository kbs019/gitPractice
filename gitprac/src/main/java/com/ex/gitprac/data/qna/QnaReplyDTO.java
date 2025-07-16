package com.ex.gitprac.data.qna;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class QnaReplyDTO {
    private int postNo;
    private int replyNo;
    private String writer;
    private String content;
    private LocalDateTime reg;
}
/*
create table qna_reply(
    postNo      number,
    replyNo     number,
    writer
);
*/
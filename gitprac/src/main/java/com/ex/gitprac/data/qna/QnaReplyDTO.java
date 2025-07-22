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
    private int status;
}
/*
create table qna_reply(
    postNo          number,
    replyNo         number,
    writer          varchar2(200),
    content         varchar2(4000)      not null,
    reg             date                default sysdate,
    status          number              default 0,
    constraint pk_qna_reply_01 primary key (replyNo),
    constraint fk_qna_reply_01 foreign key (postNo) references qna_board (postNo)
);

create sequence qna_reply_seq nocache start with 1 increment by 1;

commit;
*/
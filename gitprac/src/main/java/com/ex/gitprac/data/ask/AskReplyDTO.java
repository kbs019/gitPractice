package com.ex.gitprac.data.ask;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AskReplyDTO {
    private int askNo;
    private int replyNo;
    private String writer;
    private String content;
    private LocalDateTime reg;
}
/*
create table ask_reply(
    replyNo         number,
    askNo           number,
    writer          varchar2(200),
    content         varchar2(4000)      not null,
    reg             date                default sysdate,
    constraint pk_ask_reply primary key (replyNo),
    constraint fk_ask_reply foreign key (askNo) references ask (askNo)
);

create sequence ask_reply_seq nocache start with 1 increment by 1;

commit;
*/
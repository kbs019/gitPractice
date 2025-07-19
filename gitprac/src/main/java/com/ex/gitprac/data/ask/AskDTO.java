package com.ex.gitprac.data.ask;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AskDTO {
    private int askNo;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime reg;
    private int category;
    private int views;
    private String imgName;
    private String imgPath;
    private String originalName;
    private int isAnswered;
}
/* 
create table ask(
    askNo           number,
    title           varchar2(50)        not null,
    writer          varchar2(50),
    content         varchar2(4000)      not null,
    reg             date                default sysdate,
    category        number,
    views           number              default 0,
    imgName         varchar2(500),
    imgPath         varchar2(500),
    originalName    varchar2(500),
    isAnswered      number              default 0,
    constraint pk_ask primary key (askNo)
);

create sequence ask_seq nocache start with 1 increment by 1;

commit;
*/

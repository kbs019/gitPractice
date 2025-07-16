package com.ex.gitprac.data.qna;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class QnaBoardDTO {
    private int postNo;
    private String writer;
    private String title;
    private String content;
    private String imgName;
    private String imgPath;
    private String originalName;
    private int showRecord;
    private LocalDateTime reg;
    private int views;
    private int status;
    private int isAnswered;
}

/*
create table qna_board(
    postNo          number,
    writer          varchar2(20)    not null,
    title           varchar2(50)    not null,
    content         varchar2(4000)    not null,
    imgName         varchar2(500),
    imgPath         varchar2(500),
    originalName    varchar2(500),
    showRecord      number          default 0,
    reg             date            default sysdate,
    views           number          default 0,
    status          number          default 0,
    isAnswered      number          default 0,
    constraint pk_qna_board primary key (postNo)
);

create sequence qna_board_seq nocache start with 1 increment by 1;

commit;    

select * from qna_board;

insert into qna_board values( qna_board_seq.nextval, ?, ?, ?, ?, ?, ?, 0, sysdate, 0, 0, 0 );

update qna_board set writer=#{writer} ...

delete from qna_board where postNo=#{postNo}
*/
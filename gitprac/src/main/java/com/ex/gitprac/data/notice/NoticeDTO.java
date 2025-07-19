package com.ex.gitprac.data.notice;         // 고객센터 라는 이름의 center 패키지

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoticeDTO {
    private int noticeNo;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime reg;
    private int views;
    private String imgName;
    private String imgPath;
    private String originalName;
}
/*
create table notice(
    noticeNo        number,
    title           varchar2(50)        not null,
    writer          varchar2(50),
    content         varchar2(4000)      not null,
    reg             date                default sysdate,
    views           number              default 0,
    imgName         varchar2(500),
    imgPath         varchar2(500),
    originalName    varchar2(500),
    constraint pk_notice primary key (noticeNo)
);

create sequence notice_seq nocache start with 1 increment by 1;

commit;

select * from notice;

drop table notice;

*/
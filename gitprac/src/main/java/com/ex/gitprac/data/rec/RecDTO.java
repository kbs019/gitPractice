package com.ex.gitprac.data.rec;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RecDTO {
    private int recNo;
    private String writer;
    private int petNo;
    private String category;
    private String title;
    private String content;
    private LocalDateTime reg;
    private String orgImgName;
    private String imgName;
    private String imgPath;
}

/*
create table rec(
    recNo           number              primary key,
    writer          varchar2(200),
    petNo           number,
    category        varchar2(30)        not null,
    title           varchar2(200)       not null,
    content         varchar2(4000)      not null,
    reg             date                default sysdate,
    orgImgName      varchar2(500),
    imgName         varchar2(500),
    imgPath         varchar2(500)
);

create sequence rec_seq nocache start with 1 increment by 1;

commit;
*/
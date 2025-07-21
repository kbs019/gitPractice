package com.ex.gitprac.data.info;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InfoBoardDTO {
    private int     postNo;
    private String  category;
    private String  title;
    private String  writer;
    private String  content;
    private LocalDateTime reg;
    private int     views;
    private int     status;
    private int     ref;
}
/* 
create table info_board (
    postNo    number            primary key,
    category   varchar2(20)      not null,
    title      varchar2(20)      not null,
    writer     varchar2(20)      not null,
    content    varchar2(4000)    not null,
    reg        date              default sysdate,
    views      number            default 0,
    status   number            default 0, 
    ref        number            default 0
);

create sequence seq_info_post start with 1 increment by 1 nocache;
ALTER TABLE info_board MODIFY title   VARCHAR2(200 BYTE);
commit;
*/

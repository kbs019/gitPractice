package com.ex.gitprac.data.info;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InfoReplyDTO {
    private int     replyNo;
    private int     postNo;
    private String  writer;
    private String  content;
    private LocalDateTime reg;
    private int     ref;
    private int status;
}

/* 
    CREATE TABLE info_reply (
        replyNo     NUMBER          PRIMARY KEY,
        postNo      NUMBER,
        writer      VARCHAR2(50),
        content     VARCHAR2(4000)  NOT NULL,
        reg         DATE            DEFAULT sysdate,
        ref         NUMBER          DEFAULT 0,
        CONSTRAINT fk_info_reply_post FOREIGN KEY (postNo) REFERENCES info_board(postNo) ON DELETE CASCADE
    );

    CREATE SEQUENCE seq_info_reply START WITH 1 INCREMENT BY 1;
*/
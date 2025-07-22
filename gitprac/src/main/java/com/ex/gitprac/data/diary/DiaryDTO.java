package com.ex.gitprac.data.diary;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DiaryDTO {

    private Integer diaryNo;
    private String writer;
    private String content;
    private LocalDate reg;
    private String imgName;
    private String imgPath;
    private String originalName;
    private String status;
}
/*
CREATE TABLE DIARY(

    diaryNo NUMBER PRIMARY KEY,
    writer VARCHAR2(200),
    content VARCHAR2(4000),
    reg DATE DEFAULT SYSDATE,
    imgName VARCHAR2(500),
    imgPath VARCHAR2(500),
    originalName VARCHAR2(500),
    status NUMBER DEFAULT 0

);

CREATE SEQUENCE DIARY_SEQ NO CACHE;

SELECT * FROM DIARY;

INSERT INTO DIARY VALUES(DIARY_SEQ_NEXTVAL, ?, ?, SYSDATE, ?, ?, ?, 0);

DROP TABLE DIARY;

DROP SEQUENCE DIARY_SEQ;

COMMIT;
*/
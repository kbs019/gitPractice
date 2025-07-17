package com.ex.gitprac.data.diary;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DiaryDTO {

    private int diaryNo;
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
    DIARY_NO NUMBER PRIMARY KEY,
    WRITER VARCHAR2(50),
    CONTENT VARCHAR2(500),
    REG DATE DEFAULT SYSDATE,
    IMG_NAME VARCHAR2(500),
    IMG_PATH VARCHAR2(500),
    ORIGINALNAME VARCHAR2(500),
    STATUS NUMBER DEFAULT 0
);

CREATE SEQUENCE DIARY_SEQ NO CACHE;

SELECT * FROM DIARY;

INSERT INTO DIARY VALUES(DIARY_SEQ_NEXTVAL, ?, ?, SYSDATE, ?, ?, ?, 0);

DROP TABLE DIARY;

DROP SEQUENCE DIARY_SEQ;

COMMIT;
*/
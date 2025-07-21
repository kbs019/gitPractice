package com.ex.gitprac.data.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {
    private String id;              // 아이디
    private String pw;              // 패스워드
    private String nick;            // 닉네임
    private String name;            // 이름
    private LocalDate birth;        // 생년월일
    private LocalDateTime reg;      // 가입날짜
    private String email;           // 이메일
    private String phone;           // 휴대폰
    private String carrier;         // 통신사
    private int status;             // 상태
    private int role;               // 등급
    private LocalDate bannedUntil;  // 제재 마지막 날짜
}

/*

CREATE TABLE users (
    id         VARCHAR2(20)         PRIMARY KEY,
    pw         VARCHAR2(20)         NOT NULL,
    nick       VARCHAR2(20)         UNIQUE NOT NULL,
    name       VARCHAR2(20)         NOT NULL,
    birth      DATE                 NOT NULL,
    reg        DATE                 DEFAULT SYSDATE,
    email      VARCHAR2(50)         NOT NULL,
    phone      VARCHAR2(11)         NOT NULL,
    carrier    VARCHAR2(20)         NOT NULL,
    status     NUMBER               DEFAULT 0,
    role       NUMBER               DEFAULT 0
);

*/
/* 
alter table users add bannedUntil date; 
ALTER TABLE users MODIFY id   VARCHAR2(200 BYTE);
ALTER TABLE users MODIFY nick VARCHAR2(200 BYTE);
ALTER TABLE users MODIFY name VARCHAR2(200 BYTE);
*/
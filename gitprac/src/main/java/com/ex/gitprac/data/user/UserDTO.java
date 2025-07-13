package com.ex.gitprac.data.user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {
    private String id;              // 아이디
    private String pw;              // 패스워드
    private String nick;            // 닉네임
    private String name;            // 이름
    private LocalDateTime birth;    // 생년월일
    private LocalDateTime reg;      // 가입날짜
    private String email;           // 이메일
    private String phone;           // 휴대폰
    private String carrier;         // 통신사
    private int status;             // 상태
    private int role;               // 등급
}

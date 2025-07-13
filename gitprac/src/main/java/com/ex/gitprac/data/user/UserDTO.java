package com.ex.gitprac.data.user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {
    private String id;              // 아이디
    private String pw;              // 패스워드
    private String nick;            // 닉네임
    private LocalDateTime reg;      // 가입날짜
    private LocalDateTime birth;    // 생년월일
    private int status;             // 상태
    private String Email;           // 이메일
}

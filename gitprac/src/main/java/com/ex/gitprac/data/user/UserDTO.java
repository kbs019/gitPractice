package com.ex.gitprac.data.user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String pw;
    private String nick;
    private LocalDateTime reg;
    private int status;
}

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
    private int     isFixed;
    private int     ref;
}

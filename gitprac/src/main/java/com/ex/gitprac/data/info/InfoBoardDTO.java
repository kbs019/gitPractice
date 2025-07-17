package com.ex.gitprac.data.info;

import java.security.Timestamp;

import lombok.Data;

@Data
public class InfoBoardDTO {
    private int     postNo;
    private String  writer;
    private String  title;
    private String  content;
    private Timestamp reg;
    private int     views;
    private String  category;
    private int     isFixed;
}

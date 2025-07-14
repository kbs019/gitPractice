package com.ex.gitprac.data.qna;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class QnaBoardDTO {
    private int postNo;
    private String writer;
    private String title;
    private String content;
    private String imgName;
    private String imgPath;
    private String originalName;
    private int showRecord;
    private LocalDateTime reg;
    private int views;
    private int status;
    private int isAnswered;
}

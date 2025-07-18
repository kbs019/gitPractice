package com.ex.gitprac.data.rec;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RecDTO {
    private int recNo;
    private String writer;
    private int petNo;
    private String category;
    private String title;
    private String content;
    private LocalDate reg;
    private String orgImgName;
    private String imgName;
    private String imgPath;
}

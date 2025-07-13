package com.ex.gitprac.data.comu;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ComuBoardDTO {
    private int postNo;             // 게시글 번호
    private String writer;          // 작성자명
    private String title;           // 글제목
    private String content;         // 글내용
    private Timestamp reg;          // 작성일
    private String imgName;         // 이미지 이름
    private String imgPath;         // 이미지 경로
    private int views;              // 조회수
    private String category;        // 카테고리
    private int isFixed;            // 고정 여부
}
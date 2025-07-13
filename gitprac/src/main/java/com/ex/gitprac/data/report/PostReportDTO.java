package com.ex.gitprac.data.report;

import lombok.Data;

@Data
public class PostReportDTO {
    private int postNo;         // 게시글 번호
    private String id;          // 신고자 아이디
    private int complainNo;  // 신고 대상 게시글 번호
    private int isComplain;  // 신고 여부
    private int complainCount;  // 신고 횟수 
}

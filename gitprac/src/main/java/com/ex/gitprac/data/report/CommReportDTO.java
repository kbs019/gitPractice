package com.ex.gitprac.data.report;

import lombok.Data;

@Data
public class CommReportDTO {
    private int commNo;         // 댓글 식별 번호
    private String id;          // 신고자 아이디
    private int complainNo;     // 신고 대상 댓글 번호
    private int isComplain;     // 신고 여부
    private int complainCount;  // 신고 횟수
}

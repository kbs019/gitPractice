package com.ex.gitprac.repository.qna;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaBoardMapper {
    // 최대 글번호 조회
    public int maxNum();
    // 글 작성
    public int postInsert();
}

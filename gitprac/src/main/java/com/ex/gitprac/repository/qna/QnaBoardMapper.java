package com.ex.gitprac.repository.qna;

import org.apache.ibatis.annotations.Mapper;

import com.ex.gitprac.data.qna.QnaBoardDTO;

@Mapper
public interface QnaBoardMapper {
    // 글 작성
    public int postInsert(QnaBoardDTO qto);
}

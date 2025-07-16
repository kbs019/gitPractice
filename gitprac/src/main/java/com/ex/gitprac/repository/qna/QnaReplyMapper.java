package com.ex.gitprac.repository.qna;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.gitprac.data.qna.QnaReplyDTO;

@Repository
@Mapper
public interface QnaReplyMapper {
    // 답변 작성
    public int replyInsert( @Param("postNo") int postNo, @Param("writer") String writer, @Param("content") String content );
    // 답변 리스트 조회
    public QnaReplyDTO replySelect( int postNo );
}

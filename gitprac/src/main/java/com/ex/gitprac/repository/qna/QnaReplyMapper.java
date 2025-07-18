package com.ex.gitprac.repository.qna;

import java.util.List;

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
    public List<QnaReplyDTO> replySelect( int postNo );

    // 답변 삭제
    public int replyDelete( int replyNo );

    // 답변에 대한 게시글 번호 조회
    public int selectPostNo( int replyNo );

    // 현재 게시글에 달려있는 답변의 수 조회
    public int selectReplyNoCount( int postNo );

    // 게시글 삭제로 인한 해당 게시글의 모든 답변 삭제
    public void allReplyDelete(int postNo);
}

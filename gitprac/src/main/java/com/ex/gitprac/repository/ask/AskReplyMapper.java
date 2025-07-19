package com.ex.gitprac.repository.ask;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.gitprac.data.ask.AskReplyDTO;

@Repository
@Mapper
public interface AskReplyMapper {
    // 답변 작성
    public int replyInsert( @Param("askNo") int askNo, @Param("writer") String writer, @Param("content") String content );

    // 답변 리스트 조회
    public List<AskReplyDTO> replyList( int postNo );

    // 답변 삭제
    public int replyDelete( int replyNo );

    // 답변에 대한 게시글 번호 조회
    public int selectAskNo( int replyNo );

    // 게시글 번호에 대한 답변 수 조회
    public int selectReplyNoCount( int postNo );

    // 게시글 번호에 대한 모든 답변 삭제
    public void allReplyDelete( int postNo );
}

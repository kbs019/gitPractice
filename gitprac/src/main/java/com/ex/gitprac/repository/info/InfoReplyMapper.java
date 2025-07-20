package com.ex.gitprac.repository.info;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.gitprac.data.info.InfoReplyDTO;

@Mapper
@Repository
public interface InfoReplyMapper {
    // 댓글 쓰기
    public int insertReply(InfoReplyDTO irdto);
    
    // 댓글 리스트
    public List<InfoReplyDTO> getReply(@Param("postNo") int postNo);
    
    // 댓글 수정 
    public int updateReply(InfoReplyDTO irdto);

    // 댓글 삭제
    public int deleteReply(@Param("replyNo") int replyNo);
}

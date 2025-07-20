package com.ex.gitprac.repository.info;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.gitprac.data.info.InfoReplyDTO;

@Mapper
@Repository
public interface InfoReplyMapper {
    public int insertReply(InfoReplyDTO irdto);
    public List<InfoReplyDTO> getReply(@Param("postNo") int postNo);
    public int updateReply(InfoReplyDTO irdto);
    public int deleteReply(int replyNo);
}

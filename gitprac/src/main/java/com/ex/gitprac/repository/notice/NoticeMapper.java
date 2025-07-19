package com.ex.gitprac.repository.notice;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.gitprac.data.notice.NoticeDTO;

@Repository
@Mapper
public interface NoticeMapper {
    // 전체 게시글 갯수
    public int noticeCount();

    // 전체 게시글 목록
    public List<NoticeDTO> noticeList( @Param("start") int start, @Param("end") int end );

    // 글 작성
    public int noticeInsert( NoticeDTO nto );

    // 글 내용
    public NoticeDTO noticeContent( int noticeNo );
    
    // 조회수 증가
    public void viewsUp( int noticeNo );

    // 글 수정
    public int noticeUpdate( NoticeDTO nto );

    // 글 삭제
    public int noticeDelete( int noticeNo );
}

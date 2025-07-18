package com.ex.gitprac.repository.info;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.gitprac.data.info.InfoBoardDTO;

@Mapper
@Repository
public interface InfoBoardMapper {

    public int maxNum();
    // 글 쓰기
    public int infoBoardInsert(InfoBoardDTO infoBoardDTO);
    // 글 갯수
    public int infoBoardCount();
    // 글 리스트
    public List<InfoBoardDTO> infoBoardList(@Param("start") int start, @Param("end") int end);
    // 글 조회
    public InfoBoardDTO infoBoardContent(int num);
    // 조회수 증가
    public void viewsUp(int num);
    // 글 수정
    public int infoBoardUpdate(InfoBoardDTO infoBoardDTO);
    // 글 삭제
    public int infoBoardDelete(int num);
    // 카테고리 글 갯수
    public int infoCateBoardCount(@Param("category") String category);
    // 카테고리 글 리스트
    public List<InfoBoardDTO> infoCateBoardList(@Param("category") String category, @Param("start") int start, @Param("end") int end );
}
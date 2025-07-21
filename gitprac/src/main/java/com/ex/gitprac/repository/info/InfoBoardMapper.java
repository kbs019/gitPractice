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
    public InfoBoardDTO infoPostContent(int num);

    // 조회수 증가
    public void viewsUp(int num);

    // 글 수정
    public int infoPostUpdate(InfoBoardDTO infoBoardDTO);

    // 글 삭제
    public int infoPostDelete(int num);

    // 카테고리 글 갯수
    public int infoCateBoardCount(@Param("category") String category);
    
    // 카테고리 글 리스트
    public List<InfoBoardDTO> infoCateBoardList(@Param("category") String category, @Param("start") int start, @Param("end") int end );

    // 제재당한 인원의 닉네임이 작성한 게시글의 status 를 1 로 변경
    public void updateStatusByNick(String nick);

    // 제재 기간이 끝나 다시 status 값이 0 으로 돌아감
    public void restoreStatusByNick(String nick);

    // 작성자로 검색
    public List<InfoBoardDTO> searchByWriter(@Param("keyword") String ketword);

    // 제목으로 검색
    public List<InfoBoardDTO> searchByTitle(@Param("keyword") String keyword);
}
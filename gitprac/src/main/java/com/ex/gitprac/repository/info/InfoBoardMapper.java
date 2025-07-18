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
    public int infoBoardInsert(InfoBoardDTO infoBoardDTO);
    public int infoBoardCount();
    public List<InfoBoardDTO> infoBoardList(@Param("start") int start, @Param("end") int end);
    public InfoBoardDTO infoBoardContent(int num);
    public void readCountUp(int num);
    public int infoBoardUpdate(InfoBoardDTO infoBoardDTO);
    public int infoBoardDelete(int num);
    public int infoCateBoardCount(@Param("category") String category);
    public List<InfoBoardDTO> infoCateBoardList(@Param("category") String category, @Param("start") int start, @Param("end") int end );
}
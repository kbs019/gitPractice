package com.ex.gitprac.repository.diary;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.gitprac.data.diary.DiaryDTO;

@Mapper
public interface DiaryMapper {

    public int insertDiary(DiaryDTO ddto);
    public int countDiary(String writer);
    public List<DiaryDTO> listDiary(@Param("writer") String writer, @Param("start") int start, @Param("end") int end);
    public int countDiaryByDate(@Param("writer") String writer, @Param("startDate") String startDate, @Param("endDate") String endDate);
    public List<DiaryDTO> listDiaryByDate(@Param("writer") String writer, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("start") int start, @Param("end") int end);
    public void deleteDiary(@Param("diaryNo") int diaryNo);
    public void updateDiary(DiaryDTO dto);
    public DiaryDTO getDiary(@Param("diaryNo") int diaryNo);
}

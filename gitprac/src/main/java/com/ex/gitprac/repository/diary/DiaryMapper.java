package com.ex.gitprac.repository.diary;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.gitprac.data.diary.DiaryDTO;

@Mapper
@Repository
public interface DiaryMapper {

    public int insertDiary(DiaryDTO ddto);
    public int countDiary(String writer);
    public List<DiaryDTO> listDiary(int start, int end, String writer);
    public DiaryDTO contentDiary(int diaryNo);
}

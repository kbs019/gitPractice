package com.ex.gitprac.service.diary;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.gitprac.data.diary.DiaryDTO;
import com.ex.gitprac.repository.diary.DiaryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;

    public int insertDiary(DiaryDTO ddto){
        int result = diaryMapper.insertDiary(ddto);
        return result;
    }
    public int countDiary(String writer){
        int result = diaryMapper.countDiary(writer);
        return result;
    }
    public List<DiaryDTO> listDiary(String writer, int start, int end){
        List<DiaryDTO> list = diaryMapper.listDiary(writer, start, end);
        return list;
    }
    public DiaryDTO getDiary(int diaryNo){
        return diaryMapper.getDiary(diaryNo);
    }
    public int countDiaryByDate(String writer, String startDate, String endDate){
        int result = diaryMapper.countDiaryByDate(writer, startDate, endDate);
        return result;
    }
    public List<DiaryDTO> listDiaryByDate(String writer, String startDate, String endDate, int start, int end) {
        return diaryMapper.listDiaryByDate(writer, startDate, endDate, start, end);
    }
    public void deleteDiary(int diaryNo) {
        diaryMapper.deleteDiary(diaryNo);
    }
    public void updateDiary(DiaryDTO dto) {
        diaryMapper.updateDiary(dto);
    }
}

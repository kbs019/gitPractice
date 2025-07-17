package com.ex.gitprac.service.diary;

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
    public List<DiaryDTO> listDiary(int start, int end, String writer){
        List<DiaryDTO> list = diaryMapper.listDiary(start, end, writer);
        return list;
    }
    public DiaryDTO contentDiary(int diaryNo){
        DiaryDTO ddto = diaryMapper.contentDiary(diaryNo);
        return ddto;
    }
}

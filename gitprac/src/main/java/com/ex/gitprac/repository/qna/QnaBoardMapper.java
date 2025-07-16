package com.ex.gitprac.repository.qna;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.gitprac.data.qna.QnaBoardDTO;

@Mapper
public interface QnaBoardMapper {

    // 글갯수
    public int boardCount();
    // 글목록
    public List<QnaBoardDTO> boardList(@Param("start") int start, @Param("end") int end);

    // 글 작성
    public int postInsert(QnaBoardDTO qto);

    // 조회수 증가
    public void viewsUp(int postNo);
    // 글 내용
    public QnaBoardDTO postContent(int postNo);

    // 글 수정
    public int postUpdate(QnaBoardDTO qto);

    // 글 삭제
    public void postDelete(int postNo);

    // 일지 조회
    // public RecBoardDTO recordSelect(String writer);

    // 답변 작성에 의해 isAnswered 컬럼의 값 변경
    public void isAnsweredChange(int postNo);
}

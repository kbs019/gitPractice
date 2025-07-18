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

    // 답변 작성에 의해 isAnswered 컬럼의 값 변경
    public void isAnsweredChange(int postNo);

    // 모든 컬럼에 대한 검색결과(글목록)
    public List<QnaBoardDTO> searchListByTotal(@Param("keyword") String keyword, @Param("start") int start, @Param("end") int end);

    // category 값이 title 일 때의 검색결과에 대한 글목록
    public List<QnaBoardDTO> searchListByTitle(@Param("keyword") String keyword, @Param("start") int start, @Param("end") int end);

    // category 값이 content 일 때의 검색결과에 대한 글목록
    public List<QnaBoardDTO> searchListByContent(@Param("keyword") String keyword, @Param("start") int start, @Param("end") int end);

    // category 값이 titleAndContent 일 때의 검색결과에 대한 글목록
    public List<QnaBoardDTO> searchListByTitleAndContent(@Param("keyword") String keyword, @Param("start") int start, @Param("end") int end);

    // 모든 컬럼에 대한 검색 결과(글 갯수)
    public int searchListCountByTotal(String keyword);
    // category 값이 title 일 때의 검색결과에 대한 글갯수
    public int searchListCountByTitle(String keyword);
    // category 값이 content 일 때의 검색결과에 대한 글갯수
    public int searchListCountByContent(String keyword);
    // category 값이 titleAndContent 일 때의 검색결과에 대한 글갯수
    public int searchListCountByTitleAndContent(String keyword);

    // 모든 컬럼에 대한 검색결과 + 답변 완료됨(글목록) 
    public List<QnaBoardDTO> searchListByTotal2(@Param("keyword") String keyword, @Param("isAnswered") int isAnswered, @Param("start") int start, @Param("end") int end);
    
    // category 값이 title 일 때의 검색결과 + 답변 완료됨에 대한 글목록
    public List<QnaBoardDTO> searchListByTitle2(@Param("keyword") String keyword, @Param("isAnswered") int isAnswered, @Param("start") int start, @Param("end") int end);
    
    // category 값이 content 일 때의 검색결과 + 답변 완료됨에 대한 글목록
    public List<QnaBoardDTO> searchListByContent2(@Param("keyword") String keyword, @Param("isAnswered") int isAnswered, @Param("start") int start, @Param("end") int end);
    
    // category 값이 titleAndContent 일 때의 검색결과 + 답변 완료됨에 대한 글목록
    public List<QnaBoardDTO> searchListByTitleAndContent2(@Param("keyword") String keyword, @Param("isAnswered") int isAnswered, @Param("start") int start, @Param("end") int end);
    
    // 모든 컬럼에 대한 검색 결과 + 답변 완료됨(글 갯수)
    public int searchListCountByTotal2(@Param("keyword") String keyword, @Param("isAnswered") int isAnswered);
    // category 값이 title 일 때의 검색결과 + 답변 완료됨에 대한 글갯수
    public int searchListCountByTitle2(@Param("keyword") String keyword, @Param("isAnswered") int isAnswered);
    // category 값이 content 일 때의 검색결과 + 답변 완료됨에 대한 글갯수
    public int searchListCountByContent2(@Param("keyword") String keyword, @Param("isAnswered") int isAnswered);
    // category 값이 titleAndContent 일 때의 검색결과 + 답변 완료됨에 대한 글갯수
    public int searchListCountByTitleAndContent2(@Param("keyword") String keyword, @Param("isAnswered") int isAnswered);

    // 답변 완료된 질문만 보는 체크박스 클릭 시, 실행될 메서드
    public List<QnaBoardDTO> isAnsweredStatusChecked(@Param("start") int start, @Param("end") int end );
    // 답변 완료된 게시글의 갯수
    public int searchListCountByIsAnsweredChecked();

    // 게시글이 가지는 replyNo 가 0 이 되었을 때, isAnswered 컬럼의 값을 0 으로 변경
    public void updateIsAnswered( int postNo );
}

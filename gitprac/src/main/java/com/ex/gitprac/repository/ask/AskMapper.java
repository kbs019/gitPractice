package com.ex.gitprac.repository.ask;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ex.gitprac.data.ask.AskDTO;

@Repository
@Mapper
public interface AskMapper {
    // 전체 게시글 갯수
    public int askCount();

    // 전체 게시글 목록
    public List<AskDTO> askList( @Param("start") int start, @Param("end") int end );

    // 글 작성
    public int askInsert( AskDTO ato );

    // 글 내용
    public AskDTO askContent( int askNo );
    
    // 조회수 증가
    public void viewsUp( int askNo );

    // 글 수정
    public int askUpdate( AskDTO ato );

    // 글 삭제
    public int askDelete( int askNo );

    // 답변 작성 시, isAnswered 컬럼값 1 로  변경
    public void updateIsAnsweredUp( int askNo );

    // 게시글의 모든 답변 삭제 시, isAnswered 컬럼값 0 으로 변경
    public void updateIsAnsweredDown( int askNo );

    // isAnswered 컬럼값에 따른 게시글 갯수 조회
    public int askCountByIsAnswered( int isAnswered );

    // isAnswered 컬럼값에 따른 게시글 조회
    public List<AskDTO> askListByIsAnswered( @Param("isAnswered") int isAnswered, @Param("start") int start, @Param("end") int end );

    // 제재당한 인원의 닉네임이 작성한 게시글의 status 를 1 로 변경
    public void updateStatusByNick( String nick );

    // 제재 기간이 끝나 다시 status 값이 0 으로 돌아감
    public void restoreStatusByNick( String nick );
}

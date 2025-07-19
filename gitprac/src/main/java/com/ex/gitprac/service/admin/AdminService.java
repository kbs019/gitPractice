package com.ex.gitprac.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.gitprac.data.ask.AskDTO;
import com.ex.gitprac.data.ask.AskReplyDTO;
import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.data.notice.NoticeDTO;
import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.repository.ask.AskMapper;
import com.ex.gitprac.repository.ask.AskReplyMapper;
import com.ex.gitprac.repository.info.InfoBoardMapper;
import com.ex.gitprac.repository.notice.NoticeMapper;
import com.ex.gitprac.repository.qna.QnaBoardMapper;
import com.ex.gitprac.repository.user.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserMapper userMapper;
    private final QnaBoardMapper qnaBoardMapper;
    private final InfoBoardMapper infoBoardMapper;
    private final NoticeMapper noticeMapper;
    private final AskMapper askMapper;
    private final AskReplyMapper askReplyMapper;
    
    // ---------------------------------------------------------------------------------------------------------------------------------------
    // 회원관리
    // 전체회원조회
    public List<UserDTO> selectAllUsers( int start, int end ){
        List<UserDTO> list = new ArrayList<UserDTO>();
        
        list = userMapper.selectAllUsers(start, end);

        return list;
    }
    // 전체 회원 수
    public int selectUsersCount(){
        return userMapper.selectUsersCount();
    }
    // 검색 결과에 따른 회원 조회
    public List<UserDTO> searchUser( String keyword, int start, int end ){
        return userMapper.searchUser( keyword, start, end );
    }
    // 검색 결과에 따른 회원 수
    public int searchUserCount( String keyword ){
        return userMapper.searchUserCount( keyword );
    }

    // --------------------------------------------------------------------------------------------------------------------------------
    // 게시글 관리
    // 상담 게시판 조회
    public List<QnaBoardDTO> qnaBoardList( int start, int end ){
        List<QnaBoardDTO> list = new ArrayList<QnaBoardDTO>();

        list = qnaBoardMapper.listToAdmin( start, end );

        return list;
    }

    // 상담 게시판 게시글 갯수
    public int boardCount(){
        return qnaBoardMapper.boardCount();
    }

    // 정보 공유 게시판 조회
    public List<InfoBoardDTO> infoBoardList( int start, int end ){
        List<InfoBoardDTO> list = new ArrayList<InfoBoardDTO>();

        list = infoBoardMapper.infoBoardList( start, end );

        return list;
    }

    // 상당 게시판 게시글 갯수
    public int infoBoardCount(){
        return infoBoardMapper.infoBoardCount();
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------
    // 공지사항
    // 공지사항 리스트 조회
    public List<NoticeDTO> noticeList( int start, int end ){
        return noticeMapper.noticeList(start, end);
    }

    // 공지사항 전체 게시글 갯수
    public int noticeCount(){
        return noticeMapper.noticeCount();
    }

    // 공지사항 글 작성
    public int noticeInsert( NoticeDTO nto ){
        return noticeMapper.noticeInsert( nto );
    }

    // 공지사항 글 내용 + (조회수 + 1)
    public NoticeDTO noticeContent( int noticeNo ){
        noticeMapper.viewsUp(noticeNo);

        return noticeMapper.noticeContent( noticeNo );
    }

    // 공지사항 글 내용 조회
    public NoticeDTO noticeContentForBtns( int noticeNo ){
        return noticeMapper.noticeContent(noticeNo);
    }

    // 공지사항 글 수정
    public int noticeUpdate( NoticeDTO nto ){
        return noticeMapper.noticeUpdate(nto);
    }

    // 공지사항 글 삭제
    public int noticeDelete( int noticeNo ){
        return noticeMapper.noticeDelete(noticeNo);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    // 질의응답
    // 질의응답 리스트 조회
    public List<AskDTO> askList( int start, int end ){
        return askMapper.askList(start, end);
    }

    // 질의응답 게시글 총 갯수
    public int askCount(){
        return askMapper.askCount();
    }

    // 질의응답 글 작성
    public int askInsert( AskDTO ato ){
        return askMapper.askInsert(ato);
    }

    // 질의응답 글 내용 + (조회수 증가)
    public AskDTO askContent( int askNo ){
        askMapper.viewsUp(askNo);
        return askMapper.askContent(askNo);
    }

    // 질의응답 글 내용 (수정 전)
    public AskDTO askContentForBtns( int askNo ){
        return askMapper.askContent(askNo);
    }

    // 글 수정
    public int askUpdate( AskDTO ato ){
        return askMapper.askUpdate(ato);
    }

    // 글 삭제 + 해당 글의 답변 모두 삭제
    public int askDelete( int askNo ){
        askReplyMapper.allReplyDelete(askNo);
        return askMapper.askDelete(askNo);
    }

    // isAnswered 컬럼값에 의한 게시글 갯수 조회
    public int askCountByIsAnswered( int isAnswered ){
        return askMapper.askCountByIsAnswered( isAnswered );
    }

    // isAnswered 컬럼값에 의한 게시글 조회
    public List<AskDTO> askListByIsAnswered( int isAnswered, int start, int end ){
        return askMapper.askListByIsAnswered( isAnswered, start, end );
    }

    // 답변 리스트 조회
    public List<AskReplyDTO> replyList( int askNo ){
        return askReplyMapper.replyList(askNo);
    }

    // 답변 작성 + 게시
    public int replyInsert( int askNo, String writer, String content ){
        askMapper.updateIsAnsweredUp( askNo );
        return askReplyMapper.replyInsert(askNo, writer, content);
    }

    // 답변 삭제 + 답변에 대한 게시글 번호 조회 + 게시글 번호에 대한 답변 수 조회 + 답변 수 0 이면, isAnswered 컬럼값 down
    public int replyDelete( int replyNo ){
        // 답변에 대한 게시글 번호 먼저 조회
        int findAskNo = askReplyMapper.selectAskNo(replyNo);

        // 답변 삭제
        int result = askReplyMapper.replyDelete(replyNo);

        // 찾은 게시글 번호로 게시글이 가지는 답변 수 조회
        int replyNoCount = askReplyMapper.selectReplyNoCount(findAskNo);

        // 게시글이 가지는 답변 수가 0 이면, isAnswered 컬럼값 0 으로 변경
        if( replyNoCount == 0 ){
            askMapper.updateIsAnsweredDown(findAskNo);
        }

        return result;
    }
}

package com.ex.gitprac.service.qna;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.repository.qna.QnaBoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaBoardService {

    private final QnaBoardMapper qnaBoardMapper;
    // 글 갯수
    public int boardCount(){
        return qnaBoardMapper.boardCount();
    }
    // 글 목록  (list() 에서 사용)
    public List<QnaBoardDTO> boardList(int start, int end){
        List<QnaBoardDTO> list = qnaBoardMapper.boardList(start, end);
        return list;
    }

    // 글 작성 (write() 에서 사용)
    public int postInsert( QnaBoardDTO qto ){
        int result = 0;

        if( qnaBoardMapper.postInsert(qto) == 1 ){
            result = 1;
        }
        
        return result;
    }

    // 조회수 증가 & 글 내용 조회
    public QnaBoardDTO postContent( int postNo ){
        qnaBoardMapper.viewsUp(postNo);

        return qnaBoardMapper.postContent(postNo);
    }

    // 글내용 조회 (updateForm() 에서 사용)
    public QnaBoardDTO showContent( int postNo ){
        return qnaBoardMapper.postContent(postNo);
    }

    // 글 수정 (updatePro() 에서 사용)
    public int postUpdate( QnaBoardDTO qto ){
        return qnaBoardMapper.postUpdate(qto);
    }

    // 글 삭제 (delete() 에서 사용)
    public void postDelete( int postNo ){
        qnaBoardMapper.postDelete(postNo);
    }

    // 일지 조회 (showRecord 팝업창에서 사용)
    // public RecBoardDTO showRecord( String writer ){ RecBoardDTO rto = qnaBoardMapper.showRecord(writer); return rto; }
}
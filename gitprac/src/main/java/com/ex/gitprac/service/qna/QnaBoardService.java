package com.ex.gitprac.service.qna;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.data.qna.QnaReplyDTO;
import com.ex.gitprac.repository.qna.QnaBoardMapper;
import com.ex.gitprac.repository.qna.QnaReplyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaBoardService {

    private final QnaBoardMapper qnaBoardMapper;
    private final QnaReplyMapper qnaReplyMapper;
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
        qnaReplyMapper.allReplyDelete(postNo);          // 여기 게시글 삭제할 때, 모든 답변 삭제 기능 추가
        qnaBoardMapper.postDelete(postNo);
    }

    // 일지 조회 (showRecord 팝업창에서 사용)
    // public RecBoardDTO showRecord( String writer ){ RecBoardDTO rto = qnaBoardMapper.showRecord(writer); return rto; }

    // 답변 작성 + isAnswered 컬럼의 값 변경
    public int replyInsert( int postNo, String writer, String content ){
        int result = 0;

        if( qnaReplyMapper.replyInsert(postNo, writer, content) == 1 ){
            qnaBoardMapper.isAnsweredChange(postNo);
            result = 1;
        }

        return result;
    }

    // 답변 리스트 조회
    public List<QnaReplyDTO> replySelect( int postNo ){
        
        return qnaReplyMapper.replySelect(postNo);
    }

    // 답변삭제 다시 진행해야함 -- 답글에 대한 postNo 조회 / 답변 삭제 / postNo 에 들어있는 replyNo 갯수 조회 / replyNo 갯수 0 이면, isAnswered 컬럼 0 으로 변경
    // 답변 삭제
    public int replyDelete( int replyNo ){
        return qnaReplyMapper.replyDelete(replyNo);
    }

    // 답변 삭제
    public int replyDeleteNew( int replyNo ){
        int result = 0;

        int selectPostNo = qnaReplyMapper.selectPostNo(replyNo);

        if(qnaReplyMapper.replyDelete(replyNo) == 1){
            int replyNoCount = qnaReplyMapper.selectReplyNoCount( selectPostNo );
            
            if( replyNoCount == 0 ){
                qnaBoardMapper.updateIsAnswered(selectPostNo);
            }
            result = 1;
        }

        return result;
    }

    // 검색 결과에 따른 글목록 조회
    public List<QnaBoardDTO> searchBoardList( String category, String keyword, int start, int end ){
        List<QnaBoardDTO> list = new ArrayList<QnaBoardDTO>();

        if( category.equals("total") ){
            list = qnaBoardMapper.searchListByTotal(keyword, start, end);
        } else if( category.equals("title") ){
            list = qnaBoardMapper.searchListByTitle(keyword, start, end);
        } else if( category.equals("content") ){
            list = qnaBoardMapper.searchListByContent(keyword, start, end);
        } else if( category.equals("titleAndContent") ){
            list = qnaBoardMapper.searchListByTitleAndContent(keyword, start, end);
        }

        return list;
    }

    // 검색 결과에 따른 글갯수 조회
    public int searchListCount( String category, String keyword ){
        int count = 0;

        if( category.equals("total") ){
            count = qnaBoardMapper.searchListCountByTotal(keyword);
        } else if( category.equals("title") ){
            count = qnaBoardMapper.searchListCountByTitle(keyword);
        } else if( category.equals("content") ){
            count = qnaBoardMapper.searchListCountByContent(keyword);
        } else if( category.equals("titleAndContent") ){
            count = qnaBoardMapper.searchListCountByTitleAndContent(keyword);
        }

        return count;
    }

    // 답변 완료된 글목록 조회
    public List<QnaBoardDTO> isAnsweredStatusChecked(int start, int end){
        List<QnaBoardDTO> list = qnaBoardMapper.isAnsweredStatusChecked(start, end);

        return list;
    }
    // 답변 완료된 글갯수
    public int searchListCountByIsAnsweredChecked(){
        int result = qnaBoardMapper.searchListCountByIsAnsweredChecked();
        return result;
    }
    
    // 검색 결과에 따른 글목록 조회
    public List<QnaBoardDTO> searchBoardList2( String category, String keyword, int isAnswered, int start, int end ){
        List<QnaBoardDTO> list = new ArrayList<QnaBoardDTO>();

        if( isAnswered == 0 ){
            if( category.equals("total") ){
                list = qnaBoardMapper.searchListByTotal(keyword, start, end);
            } else if( category.equals("title") ){
                list = qnaBoardMapper.searchListByTitle(keyword, start, end);
            } else if( category.equals("content") ){
                list = qnaBoardMapper.searchListByContent(keyword, start, end);
            } else if( category.equals("titleAndContent") ){
                list = qnaBoardMapper.searchListByTitleAndContent(keyword, start, end);
            }
        } else if( isAnswered == 1 ){
            if( category.equals("total") ){
                list = qnaBoardMapper.searchListByTotal2(keyword, isAnswered, start, end);
            } else if( category.equals("title") ){
                list = qnaBoardMapper.searchListByTitle2(keyword, isAnswered, start, end);
            } else if( category.equals("content") ){
                list = qnaBoardMapper.searchListByContent2(keyword, isAnswered, start, end);
            } else if( category.equals("titleAndContent") ){
                list = qnaBoardMapper.searchListByTitleAndContent2(keyword, isAnswered, start, end);
            }
        }

        return list;
    }

    // 검색 결과에 따른 글갯수 조회
    public int searchListCount2( String category, String keyword, int isAnswered ){
        int count = 0;

        if( isAnswered == 0 ){
            if( category.equals("total") ){
                count = qnaBoardMapper.searchListCountByTotal(keyword);
            } else if( category.equals("title") ){
                count = qnaBoardMapper.searchListCountByTitle(keyword);
            } else if( category.equals("content") ){
                count = qnaBoardMapper.searchListCountByContent(keyword);
            } else if( category.equals("titleAndContent") ){
                count = qnaBoardMapper.searchListCountByTitleAndContent(keyword);
            }
        } else if( isAnswered == 1 ){
            if( category.equals("total") ){
                count = qnaBoardMapper.searchListCountByTotal2(keyword, isAnswered);
            } else if( category.equals("title") ){
                count = qnaBoardMapper.searchListCountByTitle2(keyword, isAnswered);
            } else if( category.equals("content") ){
                count = qnaBoardMapper.searchListCountByContent2(keyword, isAnswered);
            } else if( category.equals("titleAndContent") ){
                count = qnaBoardMapper.searchListCountByTitleAndContent2(keyword, isAnswered);
            }
        }

        return count;
    }
}
package com.ex.gitprac.service.info;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.data.info.InfoReplyDTO;
import com.ex.gitprac.repository.info.InfoBoardMapper;
import com.ex.gitprac.repository.info.InfoReplyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InfoBoardService {
    
    private final InfoBoardMapper infoBoardMapper;
    private final InfoReplyMapper infoReplyMapper;
    // 글 개수
    public int count() {
        return infoBoardMapper.infoBoardCount();
    }

    // 글 목록
    public List<InfoBoardDTO> infoBoardList(int start, int end){
        
        List<InfoBoardDTO> list = infoBoardMapper.infoBoardList(start, end);
        return list;
    }
    
    // 카테 글 개수
    public int cateCount( String category) {
        int result = infoBoardMapper.infoCateBoardCount(category);
        System.out.println(">>> 카테고리 '" + category + "'의 게시글 수: " + result);
        return result;
    }

    // 카테 글 리스트
    public List<InfoBoardDTO> infoCateBoardList(String category, int start, int end) {
        List<InfoBoardDTO> cateList = infoBoardMapper.infoCateBoardList(category, start, end);
        return cateList;
    }
    
    // 글 쓰기
    public int infoInsert( InfoBoardDTO idto ) {
        System.out.println(">>> 글쓰기 전 postNo: " + idto.getPostNo());
        
        int result = infoBoardMapper.infoBoardInsert(idto);

        System.out.println(">>> 글쓰기 후 postNo: " + idto.getPostNo());
        return result == 1 ? 1 : 0;
    }
    
    // 글 조회
    public InfoBoardDTO InfopostContent( int postNo ) {
        return infoBoardMapper.infoPostContent(postNo);
    }

    // 글 수정
    public int infoPostUpdate ( InfoBoardDTO idto) {
        return infoBoardMapper.infoPostUpdate(idto);
    }
    // 글 삭제
    public void infoPostDelete( int postNo ) {
        infoBoardMapper.infoPostDelete(postNo);
    }

    // 조회수 증가
    public void viewsUp( int postNo) {
        infoBoardMapper.viewsUp(postNo);
    }

    // 댓글 쓰기
    public int insertReply(InfoReplyDTO irdto) {
        return infoReplyMapper.insertReply(irdto);
    }

    // 댓글 리스트
    public List<InfoReplyDTO> getReply(int postNo) {
        return infoReplyMapper.getReply(postNo);
    }

    // 댓글 수정
    public int updateReply(InfoReplyDTO irdto) {
        return infoReplyMapper.updateReply(irdto);
    }

    // 댓글 삭제
    public int deleteReply(int replyNo) {
        return infoReplyMapper.deleteReply(replyNo);
    }

    // 검색
    public List<InfoBoardDTO> searchByOption(String option, String keyword) {
        if ("writer".equals(option)) {
            return infoBoardMapper.searchByWriter(keyword);
        } else if ("title".equals(option)) {
            return infoBoardMapper.searchByTitle(keyword);
        } else {
            return Collections.emptyList();
        }
    }
}
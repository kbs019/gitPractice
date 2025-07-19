package com.ex.gitprac.service.info;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.repository.info.InfoBoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InfoBoardService {
    
    private final InfoBoardMapper infoBoardMapper;

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
}
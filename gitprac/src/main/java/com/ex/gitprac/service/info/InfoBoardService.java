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

    public int count() {
        return infoBoardMapper.infoBoardCount();
    }


    public List<InfoBoardDTO> infoBoardList(int start, int end){
        
        List<InfoBoardDTO> list = infoBoardMapper.infoBoardList(start, end);
        return list;
    }


    public int infoInsert( InfoBoardDTO idto ) {
        System.out.println(">>> 글쓰기 전 postNo: " + idto.getPostNo());

        int result = infoBoardMapper.infoBoardInsert(idto);

        System.out.println(">>> 글쓰기 후 postNo: " + idto.getPostNo());
        return result == 1 ? 1 : 0;
    }


    public int cateCount( String category) {
        int result = infoBoardMapper.infoCateBoardCount(category);
        System.out.println(">>> 카테고리 '" + category + "'의 게시글 수: " + result);
        return result;
    }

    
    public List<InfoBoardDTO> infoCateBoardList(String category, int start, int end) {
        List<InfoBoardDTO> cateList = infoBoardMapper.infoCateBoardList(category, start, end);
        return cateList;
    }
}

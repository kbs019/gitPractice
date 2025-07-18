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
        int result = 0;
        if( infoBoardMapper.infoBoardInsert(idto) == 1) {
            result = 1;
        }
        return result;
    }
    public int countByCategory(String category) {
        return infoBoardMapper.infoCountByCategory(category);
    }
    public List<InfoBoardDTO> infoBoardListByCategory(String category, int start, int end) {
        List<InfoBoardDTO> caList = infoBoardMapper.infoBoardByCategory(start, start, category);
        return caList;
    }
}

package com.ex.gitprac.service.qna;

import org.springframework.stereotype.Service;

import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.repository.qna.QnaBoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaBoardService {

    private final QnaBoardMapper qnaBoardMapper;

    public int postInsert( QnaBoardDTO qto ){
        int result = 0;

        if( qnaBoardMapper.postInsert(qto) == 1 ){
            result = 1;
        }
        
        return result;
    }
}

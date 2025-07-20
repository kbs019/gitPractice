package com.ex.gitprac.service.myPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.ex.gitprac.data.ask.AskDTO;
import com.ex.gitprac.data.diary.DiaryDTO;
import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.data.pet.PetDTO;
import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.repository.myPage.MyPageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public int updateUser(UserDTO udto){ 
        return myPageMapper.updateUser(udto);
    };

    public int changeWriter(@Param("newWriter")String newWriter, @Param("pastWriter")String pastWriter){
        myPageMapper.changeWriterFromQNA(newWriter, pastWriter);
        myPageMapper.changeWriterFromQNARe(newWriter, pastWriter);
        myPageMapper.changeWriterFromAsk(newWriter, pastWriter);
        myPageMapper.changeWriterFromInfo(newWriter, pastWriter);
        myPageMapper.changeWriterFromDiary(newWriter, pastWriter);
        // myPageMapper.changeWriterFromInfoRe(udto, pastWriter);
        
        return 1;
    }
    
    public int pwCheck(@Param("id") String id, @Param("pw") String pw){
        
        int result = 0;
        String pastPw = (String)myPageMapper.pwCheck(id);
        if(pw.equals(pastPw)){
            result = 1;
        }
        
        return result;
    }

    public UserDTO getUser(String id){

        UserDTO dto = myPageMapper.getUser(id);
        return dto;
    }

    public int deleteUser(String id){
        return myPageMapper.deleteUser(id);
    };
    
    public PetDTO getPet(int petNo){ 
        return myPageMapper.getPet(petNo); 
    };
    
    public List<PetDTO> listPet(String id){
        return myPageMapper.listPet(id);
    }
    public int insertPet(PetDTO pdto){
        return myPageMapper.insertPet(pdto);
    };
    
    public int updatePet(PetDTO pdto){
        return myPageMapper.updatePet(pdto);
    };
    
    public int deletePet(int petNo){
        return myPageMapper.deletePet(petNo);
    };
    
    public void deletePetRec(int petNo){        
        myPageMapper.deletePetRec(petNo);
    };
        // 🟡 공통: 페이징 계산
    private Map<String, Object> getPagingParams(String id, int page) {
        int start = (page - 1) * 10 + 1;
        int end = page * 10;
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("start", start);
        map.put("end", end);
        return map;
    }

    // ✅ Ask 게시판
    public int countAsk(String id) {
        return myPageMapper.countAsk(id);
    }

    public List<AskDTO> listAsk(String id, int page) {
        return myPageMapper.listAsk(getPagingParams(id, page));
    }

    // ✅ Diary 게시판
    public int countDiary(String id) {
        return myPageMapper.countDiary(id);
    }

    public List<DiaryDTO> listDiary(String id, int page) {
        return myPageMapper.listDiary(getPagingParams(id, page));
    }

    // ✅ Info 게시판
    public int countInfo(String id) {
        return myPageMapper.countInfo(id);
    }

    public List<InfoBoardDTO> listInfo(String id, int page) {
        return myPageMapper.listInfo(getPagingParams(id, page));
    }

    // ✅ Qna 게시판
    public int countQna(String id) {
        return myPageMapper.countQna(id);
    }

    public List<QnaBoardDTO> listQna(String id, int page) {
        return myPageMapper.listQna(getPagingParams(id, page));
    }
}


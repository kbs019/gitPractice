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
        myPageMapper.changeWriterFromInfoRe(newWriter, pastWriter);
        myPageMapper.changeWriterFromAskRe(newWriter, pastWriter);
        myPageMapper.changeWriterFromDiary(newWriter, pastWriter);
        myPageMapper.changeWriterFromRec(newWriter, pastWriter);
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

    public List<AskDTO> listAsk(Map<String, Object> param) {
        return myPageMapper.listAsk(param);
    }

    public int countAsk(String writer) {
        return myPageMapper.countAsk(writer);
    }

    public List<InfoBoardDTO> listInfo(Map<String, Object> param) {
        return myPageMapper.listInfo(param);
    }

    public int countInfo(String writer) {
        return myPageMapper.countInfo(writer);
    }

    public List<QnaBoardDTO> listQna(Map<String, Object> param) {
        return myPageMapper.listQna(param);
    }

    public int countQna(String writer) {
        return myPageMapper.countQna(writer);
    }
}


package com.ex.gitprac.service.myPage;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import com.ex.gitprac.data.pet.PetDTO;
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

    public int changeWriter(@Param("dto")UserDTO udto, @Param("pastWriter")String pastWriter){
        myPageMapper.changeWriterFromQNA(udto, pastWriter);
        myPageMapper.changeWriterFromQNARe(udto, pastWriter);
        myPageMapper.changeWriterFromAsk(udto, pastWriter);
        myPageMapper.changeWriterFromInfo(udto, pastWriter);
        myPageMapper.changeWriterFromDiary(udto, pastWriter);
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
}

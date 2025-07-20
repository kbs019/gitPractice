package com.ex.gitprac.service.myPage;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.ex.gitprac.data.pet.PetDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.repository.myPage.MyPageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public UserDTO getUser(String id){ 
        return myPageMapper.getUser(id); 
    };

    public int updateUser(UserDTO udto){ 
        return myPageMapper.updateUser(udto);
    };

    // public int changeWriter(@Param("dto")UserDTO udto, @Param("pastWriter")String pastWriter){
    //     return myPageMapper.changeWriter(udto, pastWriter);
    // }
    
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
    
    public int deletePetRec(int petNo){
        return myPageMapper.deletePetRec(petNo);
    };
}

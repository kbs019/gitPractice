package com.ex.gitprac.service.myPage;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.ex.gitprac.data.diary.PetDTO;
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

    public void updateUser(@Param("pw") String pw, @Param("nick") String nick, @Param("email") String email, @Param("phone") String phone, @Param("carrier") String carrier, @Param("id") String id){ 
        myPageMapper.updateUser(pw, nick, email, phone, carrier, id);
    };
    
    public void deleteUser(String id){
        myPageMapper.deleteUser(id);
    };
    
    public PetDTO getPet(int petNo){ 
        return myPageMapper.getPet(petNo); 
    };
    
    public void insertPet(@Param("id") String id, @Param("petName") String petName, @Param("petBreed") String petBreed, @Param("petAge") int petAge, @Param("petSize") int petSize, @Param("petWeight") int petWeight, @Param("petBirth") LocalDate petBirth){
        myPageMapper.insertPet(id, petName, petBreed, petAge, petSize, petWeight, petBirth);
    };
    
    public void updatePet(@Param("petName")String petName, @Param("petBreed") String petBreed, @Param("petAge") int petAge, @Param("petSize") int petSize, @Param("petWeight") int petWeight, @Param("petBirth") LocalDate petBirth, @Param("petNo") int petNo){
        myPageMapper.updatePet(petName, petBreed, petAge, petSize, petWeight, petBirth, petNo);
    };
    
    public void deletePet(int petNo){
        myPageMapper.deletePet(petNo);
    };
    
    public void deletePetRec(int petNo){
        myPageMapper.deletePetRec(petNo);
    };
}

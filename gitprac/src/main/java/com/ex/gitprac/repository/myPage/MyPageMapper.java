package com.ex.gitprac.repository.myPage;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.gitprac.data.diary.PetDTO;
import com.ex.gitprac.data.user.UserDTO;

@Mapper
public interface MyPageMapper {

    public UserDTO getUser(String id);
    public void updateUser(@Param("pw") String pw, @Param("nick") String nick, @Param("email") String email, @Param("phone") String phone, @Param("carrier") String carrier, @Param("id") String id);
    public void deleteUser(String id);
    public PetDTO getPet(int petNo);
    public void insertPet(@Param("id") String id, @Param("petName") String petName, @Param("petBreed") String petBreed, @Param("petAge") int petAge, @Param("petSize") int petSize, @Param("petWeight") int petWeight, @Param("petBirth") LocalDate petBirth);
    public void updatePet(@Param("petName")String petName, @Param("petBreed") String petBreed, @Param("petAge") int petAge, @Param("petSize") int petSize, @Param("petWeight") int petWeight, @Param("petBirth") LocalDate petBirth, @Param("petNo") int petNo);
    public void deletePet(int petNo);
    public void deletePetRec(int petNo);
}

package com.ex.gitprac.repository.myPage;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.gitprac.data.pet.PetDTO;
import com.ex.gitprac.data.user.UserDTO;

@Mapper
public interface MyPageMapper {

    public int updateUser(UserDTO udto);
    public String pwCheck(String id);
    public UserDTO getUser(String id);
    public int deleteUser(String id);
    public PetDTO getPet(int petNo);
    public int insertPet(PetDTO pdto);
    public int updatePet(PetDTO pdto);
    public int deletePet(int petNo);
    public void deletePetRec(int petNo);
    public int changeWriterFromQNA(@Param("udto")UserDTO udto, @Param("pastWriter")String writer);
    public int changeWriterFromQNARe(@Param("udto")UserDTO udto, @Param("pastWriter")String writer);
    public int changeWriterFromAsk(@Param("udto")UserDTO udto, @Param("pastWriter")String writer);
    public int changeWriterFromInfo(@Param("udto")UserDTO udto, @Param("pastWriter")String writer);
    public int changeWriterFromDiary(@Param("udto")UserDTO udto, @Param("pastWriter")String writer);
    // public int changeWriterFromInfoRe(@Param("udto")UserDTO udto, @Param("pastWriter")String writer);
}

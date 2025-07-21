package com.ex.gitprac.repository.myPage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ex.gitprac.data.ask.AskDTO;
import com.ex.gitprac.data.diary.DiaryDTO;
import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.data.pet.PetDTO;
import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.data.user.UserDTO;

@Mapper
public interface MyPageMapper {

    public int updateUser(UserDTO udto);
    public String pwCheck(String id);
    public UserDTO getUser(String id);
    public int deleteUser(String id);
    public PetDTO getPet(int petNo);
    public List<PetDTO> listPet(String id);
    public int insertPet(PetDTO pdto);
    public int updatePet(PetDTO pdto);
    public int deletePet(int petNo);
    public void deletePetRec(int petNo);
    public int changeWriterFromQNA(@Param("newWriter")String newWriter, @Param("pastWriter")String pastWriter);
    public int changeWriterFromQNARe(@Param("newWriter")String newWriter, @Param("pastWriter")String pastWriter);
    public int changeWriterFromAsk(@Param("newWriter")String newWriter, @Param("pastWriter")String pastWriter);
    public int changeWriterFromInfo(@Param("newWriter")String newWriter, @Param("pastWriter")String pastWriter);
    public int changeWriterFromInfoRe(@Param("newWriter")String newWriter, @Param("pastWriter")String pastWriter);
    public int changeWriterFromAskRe(@Param("newWriter")String newWriter, @Param("pastWriter")String pastWriter);
    public int changeWriterFromDiary(@Param("newWriter")String newWriter, @Param("pastWriter")String pastWriter);
    public int changeWriterFromRec(@Param("newWriter")String newWriter, @Param("pastWriter")String pastWriter);
    public int countAsk(String writer);
    public List<AskDTO> listAsk(Map<String, Object> map);
    public int countInfo(String writer);
    public List<InfoBoardDTO> listInfo(Map<String, Object> map);
    public int countQna(String writer);
    public List<QnaBoardDTO> listQna(Map<String, Object> map);
}

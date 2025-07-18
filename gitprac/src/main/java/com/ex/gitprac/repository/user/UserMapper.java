package com.ex.gitprac.repository.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import com.ex.gitprac.data.user.UserDTO;

@Repository
@Mapper
public interface  UserMapper {
    // 회원가입
    public int userInsert(UserDTO userDTO);
    // 로그인 체크
    public UserDTO loginCheck( @Param("id") String id, @Param("pw") String pw);

    // 전체 회원 조회
    public List<UserDTO> selectAllUsers( @Param("start") int start, @Param("end") int end );
    // 전체 회원 수
    public int selectUsersCount();
    // 검색 결과에 대한 회원 조회
    public List<UserDTO> searchUser( @Param("keyword") String keyword, @Param("start") int start, @Param("end") int end );
    // 검색 결과에 대한 회원수
    public int searchUserCount( String keyword );
}

package com.ex.gitprac.repository.user;

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
}

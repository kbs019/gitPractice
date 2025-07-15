package com.ex.gitprac.repository.user;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ex.gitprac.data.user.UserDTO;

@Repository
@Mapper
public interface  UserMapper {
    //회원가입
    public int userInsert(UserDTO userDTO);
}

package com.ex.gitprac.service.user;

import org.springframework.stereotype.Service;

import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.repository.user.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    // 회원가입
    public int userInsert(UserDTO userDTO) {
        return userMapper.userInsert(userDTO);
    }
}

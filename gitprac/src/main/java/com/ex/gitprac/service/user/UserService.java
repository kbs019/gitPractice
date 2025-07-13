package com.ex.gitprac.service.user;

import org.springframework.stereotype.Service;

import com.ex.gitprac.repository.user.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
}

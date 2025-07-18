package com.ex.gitprac.service.admin;

import org.springframework.stereotype.Service;

import com.ex.gitprac.repository.user.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserMapper userMapper;
}

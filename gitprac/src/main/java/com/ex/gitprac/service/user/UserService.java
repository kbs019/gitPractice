package com.ex.gitprac.service.user;

import org.springframework.stereotype.Service;

import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.repository.user.UserMapper;
import com.ex.gitprac.util.MailUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    // 회원가입
    public int userInsert( UserDTO userDTO)  {
        return userMapper.userInsert(userDTO);
    }
    // 로그인 체크
    public UserDTO loginCheck( String id, String pw) {
        return userMapper.loginCheck(id, pw);
    }
    // 아이디 찾기
    public UserDTO findIdByEmail(String email) {
        return userMapper.findIdByEmail(email);
    }

    public String findPwAndSendTempPw( String id, String email ) {
        UserDTO user = userMapper.findPwByIdEmail(id, email);
        if( user != null ) {
            String tempPW = generateTempPw();
            userMapper.updatePw(id, email, tempPW);
            MailUtil.sendTempPw(email, tempPW);
            return tempPW;
        }
        return null;
    }

    private String generateTempPw() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < 10; i++ ) { 
            int idx = (int)(Math.random() * chars.length() );
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }
}

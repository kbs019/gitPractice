package com.ex.gitprac.service.user;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.repository.ask.AskMapper;
import com.ex.gitprac.repository.info.InfoBoardMapper;
import com.ex.gitprac.repository.info.InfoReplyMapper;
import com.ex.gitprac.repository.qna.QnaBoardMapper;
import com.ex.gitprac.repository.qna.QnaReplyMapper;
import com.ex.gitprac.repository.user.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final QnaBoardMapper qnaBoardMapper;
    private final QnaReplyMapper qnaReplyMapper;
    private final InfoBoardMapper infoBoardMapper;
    private final InfoReplyMapper infoReplyMapper;
    private final AskMapper askMapper;

    // 회원가입
    public int userInsert( UserDTO userDTO)  {
        return userMapper.userInsert(userDTO);
    }
    // 로그인 체크
    public UserDTO loginCheck( String id, String pw) {
        return userMapper.loginCheck(id, pw);
    }

    // 유저 로그인 수정 (제재 기간 확인)
    public UserDTO loginCheckByBannedUntil( String id, String pw ){
        // id 에 맞는 유저 정보 조회
        UserDTO user = userMapper.findById(id);

        // 유저 정보가 없거나 || 유저정보의 pw 값과 입력된 pw 값이 다르다면
        if( user == null || !user.getPw().equals(pw) ){
            // null 전송
            return null;
        }

        // 정지 상태인 경우
        // 유저정보의 status 값이 1 이면서 유저정보의 banneduntil 값이 존재한다면
        if( user.getStatus() == 1 && user.getBannedUntil() != null ){
            LocalDate today = LocalDate.now();      // 오늘 날짜 조회
            LocalDate until = user.getBannedUntil();    // 제재된 날짜 조회

            if( today.isBefore(until) ){      // 오늘이 제재된 날짜보다 이전이라면
                return user;
            } else {                           // 정지 기간이 지났다면,
                userMapper.changeUserStatus(0, id);         // status 값을 0 으로 변경
                user.setStatus(0);                          // 현재 뽑아낸 유저 정보의 status 값을 0 으로 변경
                user.setBannedUntil(null);              // 현재 뽑아낸 유저 정보의 bannedUntil 값을 null 로 변경

                // 해당 유저의 닉네임 조회
                String nick = user.getNick();

                // 해당 유저가 작성한 글의 status 값을 0 으로 되돌리기
                qnaBoardMapper.restoreStatusByNick(nick);
                qnaReplyMapper.restoreStatusByNick(nick);
                infoBoardMapper.restoreStatusByNick(nick);
                infoReplyMapper.restoreStatusByNick(nick);
                askMapper.restoreStatusByNick(nick);
            }
        }

        return user;
    }   
    // 아이디 중복체크
    public UserDTO checkById(String id) {
        return userMapper.checkById(id);
    }
    // 닉네임 중복체크
    public UserDTO checkByNick(String nick) {
        return userMapper.checkByNick(nick);
    }
    // 이메일 중복체크
    public UserDTO checkByEmail(String id) {
        return userMapper.checkByEmail(id);
    }
}

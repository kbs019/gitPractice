package com.ex.gitprac.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.repository.info.InfoBoardMapper;
import com.ex.gitprac.repository.qna.QnaBoardMapper;
import com.ex.gitprac.repository.user.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserMapper userMapper;
    private final QnaBoardMapper qnaBoardMapper;
    private final InfoBoardMapper infoBoardMapper;
    
    // 회원관리
    // 전체회원조회
    public List<UserDTO> selectAllUsers( int start, int end ){
        List<UserDTO> list = new ArrayList<UserDTO>();
        
        list = userMapper.selectAllUsers(start, end);

        return list;
    }
    // 전체 회원 수
    public int selectUsersCount(){
        return userMapper.selectUsersCount();
    }
    // 검색 결과에 따른 회원 조회
    public List<UserDTO> searchUser( String keyword, int start, int end ){
        return userMapper.searchUser( keyword, start, end );
    }
    // 검색 결과에 따른 회원 수
    public int searchUserCount( String keyword ){
        return userMapper.searchUserCount( keyword );
    }

    // 게시글 관리
    // 상담 게시판 조회
    public List<QnaBoardDTO> qnaBoardList( int start, int end ){
        List<QnaBoardDTO> list = new ArrayList<QnaBoardDTO>();

        list = qnaBoardMapper.listToAdmin( start, end );

        return list;
    }

    // 상담 게시판 게시글 갯수
    public int boardCount(){
        return qnaBoardMapper.boardCount();
    }

    // 정보 공유 게시판 조회
    public List<InfoBoardDTO> infoBoardList( String category, int start, int end ){
        List<InfoBoardDTO> list = new ArrayList<InfoBoardDTO>();

        list = infoBoardMapper.infoCateBoardList( category, start, end );

        return list;
    }

    // 상당 게시판 게시글 갯수
    
}

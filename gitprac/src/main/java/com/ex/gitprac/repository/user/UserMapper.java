package com.ex.gitprac.repository.user;

import java.time.LocalDate;
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
    // ajax 로 인한 role 값 변경
    public int changeUserRole(@Param("role") int role, @Param("id") String id);
    // ajax 로 인한 status 값 변경
    public int changeUserStatus(@Param("status") int status, @Param("id") String id);
    // ajax 로 인한 bannedUntil 을 null 로 변경
    public int clearBanDate(String id);
    // id 에 대한 유저 검색
    public UserDTO findById( String id );
    // 회원 정지 기간 설정
    public boolean banUser( @Param("id") String id, @Param("until") LocalDate until );
    // 아이디 중복 체크 
    public UserDTO checkById(String id);
    // 닉네임 중복 체크
    public UserDTO checkByNick(String nick);
    // 이메일 중복 체크
    public UserDTO checkByEmail(String email);
    // 아이디 찾기
    public UserDTO findId(String email);
    // 비밀번호 찾기
    public UserDTO findPw(@Param("id") String id, @Param("email") String email);
}

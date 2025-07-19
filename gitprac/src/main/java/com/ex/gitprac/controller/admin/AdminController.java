package com.ex.gitprac.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.admin.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/*")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("userManage")
    public String adminPage( Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum ){
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = adminService.selectUsersCount();

        List<UserDTO> list = null;
        if( count > 0 ){
            list = adminService.selectAllUsers( start, end );
        }else {
            list = new ArrayList<UserDTO>();
        }
        
        int pageCount = (count/pageSize) + (count%pageSize == 0 ? 0 : 1);
        int pageBlock = 10;
        int startPage = (int) ((currentPage - 1) / pageBlock) * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;
        if( endPage > pageCount ){
            endPage = pageCount;
        }

        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageBlock", pageBlock);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("count", count);
        model.addAttribute("list", list);
        model.addAttribute("pageNum", pageNum);

        return "/admin/userManage2";
    }

    // 검색된 값에 대한 list 조회
    @PostMapping("searchUser")
    public String searchUser( Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum, @RequestParam("keyword") String keyword ){
        
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = adminService.searchUserCount(keyword);

        List<UserDTO> list = null;
        if( count > 0 ){
            list = adminService.searchUser( keyword, start, end );
        }else {
            list = new ArrayList<UserDTO>();
        }
        
        int pageCount = (count/pageSize) + (count%pageSize == 0 ? 0 : 1);
        int pageBlock = 10;
        int startPage = (int) ((currentPage - 1) / pageBlock) * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;
        if( endPage > pageCount ){
            endPage = pageCount;
        }

        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageBlock", pageBlock);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("count", count);
        model.addAttribute("list", list);
        model.addAttribute("pageNum", pageNum);

        return "/admin/searchUser";
    }

    // 게시글 관리로 이동
    @GetMapping("postManage")
    public String postManage( Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum, @RequestParam("category") String category ){

        int pageSize = 5;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int qnaCount = adminService.boardCount();

        List<QnaBoardDTO> qnaList = null;
        if( qnaCount > 0 ){
            qnaList = adminService.qnaBoardList( start, end );
        }else {
            qnaList = new ArrayList<QnaBoardDTO>();
        }

        int infoCount = adminService.infoCateBoardCount( category );
        List<InfoBoardDTO> infoList = null;
        if( infoCount > 0 ){
            infoList = adminService.infoCateBoardList( category, start, end );
        }else {
            infoList = new ArrayList<InfoBoardDTO>();
        }

        model.addAttribute("qnaCount", qnaCount);
        model.addAttribute("qnaList", qnaList);
        model.addAttribute("infoCount", infoCount);
        model.addAttribute("infoList", infoList);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("category", category);

        return "/admin/postManage";
    }



    // 공지사항 이동
    @GetMapping("notice")
    public String notice(){
        return "/admin/notice";
    }



    // 질의응답 이동
    @GetMapping("ask")
    public String ask(){
        return "/admin/ask";
    }
}

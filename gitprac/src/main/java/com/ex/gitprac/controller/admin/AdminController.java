package com.ex.gitprac.controller.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.data.notice.NoticeDTO;
import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.admin.AdminService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/*")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // ===================================== 회원관리 =========================================
    // 회원관리
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

    // ==================================== 게시글 관리 ======================================
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


    // ======================================= 공지사항 ========================================
    // 공지사항 이동
    @GetMapping("notice")
    public String notice( Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum ){
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = adminService.noticeCount();

        List<NoticeDTO> list = null;
        if( count > 0 ){
            list = adminService.noticeList( start, end );
        }else {
            list = new ArrayList<NoticeDTO>();
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

        return "/admin/notice";
    }

    // 글작성 페이지로 이동
    @GetMapping("noticeWrite")
    public String noticeWriteForm(){
        return "admin/noticeWriteForm";
    }

    // 글작성 작성
    @PostMapping("noticeWrite")
    public String noticeWritePro( NoticeDTO nto, Model model, @RequestParam("image") MultipartFile mf ){
        String originalName = mf.getOriginalFilename();
        String newName = UUID.randomUUID().toString().replace("-", "") + originalName;
        String uploadPath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\noticeUpload\\";
        String imgWebPath = "/noticeUpload/";

        nto.setOriginalName(originalName);
        nto.setImgName(newName);
        nto.setImgPath(imgWebPath);

        try{
            File f = new File(uploadPath + newName);
            mf.transferTo(f);
        } catch( Exception e ){
            e.printStackTrace();
        }

        int result = adminService.noticeInsert(nto);

        model.addAttribute("result", result);

        return "admin/noticeWritePro";
    }

    // 글 내용
    @GetMapping("noticeContent")
    public String noticeContent( @RequestParam("noticeNo") int noticeNo, @ModelAttribute("pageNum") int pageNum, Model model ){
        NoticeDTO nto = adminService.noticeContent( noticeNo );

        model.addAttribute("nto", nto);
        return "admin/noticeContent";
    }

    // 글 수정 페이지로 이동
    @GetMapping("noticeUpdate")
    public String noticeUpdateForm( @RequestParam("noticeNo") int noticeNo, @ModelAttribute("pageNum") int pageNum, Model model ){
        NoticeDTO nto = adminService.noticeContentForBtns(noticeNo);

        model.addAttribute("nto", nto);
        return "admin/noticeUpdateForm";
    }

    // 글 수정 DB 작업
    @PostMapping("noticeUpdate")
    public String noticeUpdatePro( @RequestParam("image") MultipartFile mf, NoticeDTO nto, @ModelAttribute("pageNum") int pageNum, Model model ){
        String originalName = mf.getOriginalFilename();
        String newName = UUID.randomUUID().toString().replace("-","") + originalName;
        String uploadPath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\noticeUpload\\";
        String imgWebPath = "/noticeUpload/";

        if( mf != null && !mf.isEmpty() ){
        // image 라는 file 타입의 input 태그에 들어온 값이 있다면
            try{
                File f = new File(uploadPath + nto.getImgName());
                if( f.exists() ){
                    f.delete();
                }

                File nf = new File(uploadPath + newName);
                mf.transferTo(nf);
            }catch( Exception e ){
                e.printStackTrace();
            }

            nto.setImgName(newName);
            nto.setImgPath(imgWebPath);
            nto.setOriginalName(originalName);
        }

        int result = adminService.noticeUpdate(nto);
        model.addAttribute("result", result);
        model.addAttribute("nto", nto);

        return "admin/noticeUpdatePro";
    }

    // 공지사항 글 삭제
    @GetMapping("noticeDelete")
    public String noticeDelete( @RequestParam("noticeNo") int noticeNo, @ModelAttribute("pageNum") int pageNum, Model model ){
        NoticeDTO nto = adminService.noticeContentForBtns(noticeNo);

        String uploadImgName = nto.getImgName();
        String uploadPath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\noticeUpload\\";

        try{
            File f = new File( uploadPath + uploadImgName );
            if( f.exists() ){
                f.delete();
            }
        }catch( Exception e ){
            e.printStackTrace();
        }

        int result = adminService.noticeDelete(noticeNo);

        model.addAttribute("result", result);
        return "admin/noticeDeletePro";
    }


    // ========================================= 질의응답 ======================================
    // 질의응답 이동
    @GetMapping("ask")
    public String ask(){
        return "/admin/ask";
    }
}

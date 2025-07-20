package com.ex.gitprac.controller.admin;

import java.io.File;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ex.gitprac.data.ask.AskDTO;
import com.ex.gitprac.data.ask.AskReplyDTO;
import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.data.notice.NoticeDTO;
import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.admin.AdminService;

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

        return "/admin/userManage3";        // 여기 2 -> 3  으로 변경
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

    // ajax 로 role 변경
    @PostMapping("changeUserRole")
    @ResponseBody
    public String changeUserRole( @RequestParam("role") int role, @RequestParam("id") String id ){
        String message = "권한 변경에 실패하였습니다.";

        int result = adminService.changeUserRole(role, id);

        if( result == 1 ){
            message = "권한을 변경하였습니다.";
        }

        return message;
    }

    // ajax 로 status 변경
    @PostMapping("changeUserStatus")
    @ResponseBody
    public String changeUserStatus( @RequestParam("status") int status, @RequestParam("id") String id ){
        int result = adminService.changeUserStatus(status, id);

        return result == 1 ? "변경을 성공했습니다." : "변경에 실패했습니다.";
    }

    // ajax 로 정지 기간 설정
    @PostMapping("banUser")
    @ResponseBody
    public String banUser( @RequestParam("id") String id, @RequestParam("period") int period ){

        boolean result = adminService.banUser(id, period);

        return result == true ? "정지 처리 되었습니다." : "정지 처리에 실패했습니다.";
    }

    // ==================================== 게시글 관리 ======================================
    // 게시글 관리로 이동
    @GetMapping("postManage")
    public String postManage( Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum ){

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

        int infoCount = adminService.infoBoardCount();
        List<InfoBoardDTO> infoList = null;
        if( infoCount > 0 ){
            infoList = adminService.infoBoardList( start, end );
        }else {
            infoList = new ArrayList<InfoBoardDTO>();
        }

        model.addAttribute("qnaCount", qnaCount);
        model.addAttribute("qnaList", qnaList);
        model.addAttribute("infoCount", infoCount);
        model.addAttribute("infoList", infoList);
        model.addAttribute("pageNum", pageNum);

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
        int result = 0;

        if( mf != null && !mf.isEmpty() ){
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
        }

        result = adminService.noticeInsert(nto);

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
        if( mf != null && !mf.isEmpty() ){
            // image 라는 file 타입의 input 태그에 들어온 값이 있다면
            String originalName = mf.getOriginalFilename();
            String newName = UUID.randomUUID().toString().replace("-","") + originalName;
            String uploadPath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\noticeUpload\\";
            String imgWebPath = "/noticeUpload/";

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
    public String ask( Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum ){
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = adminService.askCount();

        List<AskDTO> list = null;
        if( count > 0 ){
            list = adminService.askList( start, end );
        }else {
            list = new ArrayList<AskDTO>();
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

        return "/admin/ask";
    }

    @GetMapping("askWrite")
    public String askWriteForm(){
        return "/admin/askWriteForm";
    }

    @PostMapping("askWrite")
    public String askWritePro( AskDTO ato, Model model, @RequestParam("image") MultipartFile mf ){
        int result = 0;

        if( mf != null && !mf.isEmpty() ){
            String originalName = mf.getOriginalFilename();
            String newName = UUID.randomUUID().toString().replace("-", "") + originalName;
            String uploadPath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\askUpload\\";
            String imgWebPath = "/askUpload/";
    
            ato.setOriginalName(originalName);
            ato.setImgName(newName);
            ato.setImgPath(imgWebPath);
    
            try{
                File f = new File(uploadPath + newName);
                mf.transferTo(f);
            } catch( Exception e ){
                e.printStackTrace();
            }
        }

        result = adminService.askInsert(ato);

        model.addAttribute("result", result);

        return "/admin/askWritePro";
    }

    // 글 내용 ( + 조회수 증가 )
    @GetMapping("askContent")
    public String askContent( @RequestParam("askNo") int askNo, @ModelAttribute("pageNum") int pageNum, Model model ){
        AskDTO ato = adminService.askContent( askNo );

        model.addAttribute("ato", ato);
        return "admin/askContent";
    }

    // 글수정 페이지로 이동
    @GetMapping("askUpdate")
    public String askUpdateForm( @RequestParam("askNo") int askNo, @ModelAttribute("pageNum") int pageNum, Model model ){
        AskDTO ato = adminService.askContentForBtns(askNo);

        model.addAttribute("ato", ato);
        return "admin/askUpdateForm";
    }

    // 글 수정 DB 작업
    @PostMapping("askUpdate")
    public String askUpdatePro( @RequestParam("image") MultipartFile mf, AskDTO ato, Model model, @ModelAttribute("pageNum") int pageNum ){
        
        if( mf != null && !mf.isEmpty() ){
            // image 라는 file 타입의 input 태그에 들어온 값이 있다면
            String originalName = mf.getOriginalFilename();
            String newName = UUID.randomUUID().toString().replace("-", "") + originalName;
            String uploadPath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\askUpload\\";
            String imgWebPath = "/askUpload/";
            
            try{
                File f = new File(uploadPath + ato.getImgName());
                if( f.exists() ){
                    f.delete();
                }

                File nf = new File(uploadPath + newName);
                mf.transferTo(nf);
            }catch( Exception e ){
                e.printStackTrace();
            }

            ato.setImgName(newName);
            ato.setImgPath(imgWebPath);
            ato.setOriginalName(originalName);
        }

        int result = adminService.askUpdate(ato);
        model.addAttribute("result", result);
        model.addAttribute("ato", ato);

        return "admin/askUpdatePro";
    }

    @GetMapping("askDelete")
    public String askDelete( @RequestParam("askNo") int askNo, @ModelAttribute("pageNum") int pageNum, Model model ){
        AskDTO ato = adminService.askContentForBtns(askNo);

        String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\askUpload\\";
        String uploadName = ato.getImgName();

        try{
            File f = new File(uploadPath + uploadName);
            f.delete();
        }catch( Exception e ){
            e.printStackTrace();
        }

        int result = adminService.askDelete(askNo);

        model.addAttribute("result", result);

        return "admin/askDeletePro";
    }

    // userManage 에서 체크박스 선택으로 미답변 게시글 출력
    // 답변 여부 선택
    @PostMapping("askListByIsAnswered")
    public String searchList2( @RequestParam(name="pageNum", defaultValue="1") int pageNum, @RequestParam("isAnswered") int isAnswered , Model model) {
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = adminService.askCountByIsAnswered(isAnswered);
        List<AskDTO> list = adminService.askListByIsAnswered(isAnswered, start, end);
        
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

        return "/admin/askListByIsAnswered";
    }

    // ajax 로 넘어온 댓글 작성
    @PostMapping("askReplyInsert")
    @ResponseBody
    public String replyInsert( @RequestParam("askNo") int askNo, @RequestParam("writer") String writer, @RequestParam("content") String content ){

        String message = "댓글 작성에 실패하였습니다.";

        if( adminService.replyInsert(askNo, writer, content) == 1 ){
            message = "댓글 작성에 성공했습니다.";
        }

        return message;
    }

    // 댓글 리스트 출력
    @PostMapping("askReplyList")
    public String replyList( @RequestParam("askNo") int askNo, Model model ){

        List<AskReplyDTO> list = adminService.replyList(askNo);

        model.addAttribute("list", list);

        return "/admin/askReplyList";
    }

    // 답변 삭제
    @PostMapping("replyDelete")
    @ResponseBody
    public String replyDelete( @RequestParam("replyNo") int replyNo ){
        String result = "삭제를 실패하였습니다.";

        if( adminService.replyDelete(replyNo) == 1 ){
            result = "삭제를 완료하였습니다.";
        }

        return result;
    }
}

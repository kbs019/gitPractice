package com.ex.gitprac.controller.center;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ex.gitprac.data.ask.AskDTO;
import com.ex.gitprac.data.ask.AskReplyDTO;
import com.ex.gitprac.data.notice.NoticeDTO;
import com.ex.gitprac.service.center.CenterService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/center/*")
@RequiredArgsConstructor
public class CenterController {
    private final CenterService centerService;

    // 고객센터의 공지사항
    @GetMapping("notice")
    public String notice( Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum ){
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = centerService.noticeCount();

        List<NoticeDTO> list = null;
        if( count > 0 ){
            list = centerService.noticeList( start, end );
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

        return "center/notice";
    }

    // 공지사항 글 내용
    @GetMapping("noticeContent")
    public String noticeContent( @RequestParam("noticeNo") int noticeNo, @ModelAttribute("pageNum") int pageNum, Model model ){
        NoticeDTO nto = centerService.noticeContent( noticeNo );

        model.addAttribute("nto", nto);
        return "center/noticeContent";
    }

    // ======================== 질의응답 ========================
    // 질의응답 이동
    @GetMapping("ask")
    public String ask( Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum ){
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = centerService.askCount();

        List<AskDTO> list = null;
        if( count > 0 ){
            list = centerService.askList( start, end );
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

        return "/center/ask";
    }

    @GetMapping("askWrite")
    public String askWriteForm(){
        return "/center/askWriteForm";
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

        result = centerService.askInsert(ato);

        model.addAttribute("result", result);

        return "/center/askWritePro";
    }

    // 글 내용 ( + 조회수 증가 )
    @GetMapping("askContent")
    public String askContent( @RequestParam("askNo") int askNo, @ModelAttribute("pageNum") int pageNum, Model model ){
        AskDTO ato = centerService.askContent( askNo );

        model.addAttribute("ato", ato);
        return "center/askContent";
    }

    // 글수정 페이지로 이동
    @GetMapping("askUpdate")
    public String askUpdateForm( @RequestParam("askNo") int askNo, @ModelAttribute("pageNum") int pageNum, Model model ){
        AskDTO ato = centerService.askContentForBtns(askNo);

        model.addAttribute("ato", ato);
        return "center/askUpdateForm";
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

        int result = centerService.askUpdate(ato);
        model.addAttribute("result", result);
        model.addAttribute("ato", ato);

        return "center/askUpdatePro";
    }

    @GetMapping("askDelete")
    public String askDelete( @RequestParam("askNo") int askNo, @ModelAttribute("pageNum") int pageNum, Model model ){
        AskDTO ato = centerService.askContentForBtns(askNo);

        String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\askUpload\\";
        String uploadName = ato.getImgName();

        try{
            File f = new File(uploadPath + uploadName);
            f.delete();
        }catch( Exception e ){
            e.printStackTrace();
        }

        int result = centerService.askDelete(askNo);

        model.addAttribute("result", result);

        return "center/askDeletePro";
    }

    // userManage 에서 체크박스 선택으로 미답변 게시글 출력

    
    // 답변 여부 선택
    @PostMapping("askListByIsAnswered")
    public String searchList2( @RequestParam(name="pageNum", defaultValue="1") int pageNum, @RequestParam("isAnswered") int isAnswered , Model model) {
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = centerService.askCountByIsAnswered(isAnswered);
        List<AskDTO> list = centerService.askListByIsAnswered(isAnswered, start, end);
        
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

        return "/center/askListByIsAnswered";
    }

    // ajax 로 넘어온 댓글 작성
    @PostMapping("askReplyInsert")
    @ResponseBody
    public String replyInsert( @RequestParam("askNo") int askNo, @RequestParam("writer") String writer, @RequestParam("content") String content ){

        String message = "댓글 작성에 실패하였습니다.";

        if( centerService.replyInsert(askNo, writer, content) == 1 ){
            message = "댓글 작성에 성공했습니다.";
        }

        return message;
    }

    // 댓글 리스트 출력
    @PostMapping("askReplyList")
    public String replyList( @RequestParam("askNo") int askNo, Model model ){

        List<AskReplyDTO> list = centerService.replyList(askNo);

        model.addAttribute("list", list);

        return "/center/askReplyList";
    }

    // 답변 삭제
    @PostMapping("replyDelete")
    @ResponseBody
    public String replyDelete( @RequestParam("replyNo") int replyNo ){
        String result = "삭제를 실패하였습니다.";

        if( centerService.replyDelete(replyNo) == 1 ){
            result = "삭제를 완료하였습니다.";
        }

        return result;
    }
}

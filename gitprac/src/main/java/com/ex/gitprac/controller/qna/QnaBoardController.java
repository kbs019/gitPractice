package com.ex.gitprac.controller.qna;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.data.qna.QnaReplyDTO;
import com.ex.gitprac.data.user.UserDTO;

import com.ex.gitprac.service.qna.QnaBoardService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/qna/*")
@RequiredArgsConstructor
public class QnaBoardController {

    private final QnaBoardService qnaBoardService;

    // 상담 게시판의 메인 페이지인 list
    @GetMapping("list")
    public String list( Model model, @RequestParam(name="pageNum", defaultValue="1") int pageNum ){
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = qnaBoardService.boardCount();

        List<QnaBoardDTO> list = null;
        if( count > 0 ){
            list = qnaBoardService.boardList(start, end);
        }else{
            list = new ArrayList<QnaBoardDTO>();
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

        return "/qna/list";
    }

    // 글작성 버튼을 눌렀을 때, 작동
    @GetMapping("write")
    public String write(){
        return "/qna/writeForm";
    }

    // 글작성 form 에서 업로드 버튼을 눌렀을 때, 작동
    @PostMapping("write")
    public String write( @RequestParam("image") MultipartFile mf, QnaBoardDTO qto, Model model, HttpSession session ){
        // 세션에 저장된 user 객체 꺼내기
        UserDTO user = (UserDTO) session.getAttribute("users");
        // user 객체의 nick 컬럼의 값을 꺼내어 writer 에 대입
        String writer = user.getNick();
        // writer 를 qto 객체에 저장
        qto.setWriter(writer);

        // 원본 파일명을 출력
        String originalName = mf.getOriginalFilename();
        // 이미지 파일의 중복을 피하기 위해 새로운 이미지 파일의 이름 생성
        String newName = UUID.randomUUID().toString().replace("-", "")+originalName;
        // 현재 프로젝트 내의 이미지 파일을 저장할 폴더의 경로를 지정
        String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\qnaUpload\\";
        // 브라우저가 접근할 수 있는 URL 경로
        String imgWebPath = "/qnaUpload/";
        
        // 새로 생성된 이름과 경로를 qto 객체에 대입
        qto.setOriginalName(originalName);
        qto.setImgName(newName);
        qto.setImgPath(imgWebPath);

        // 경로와 파일명을 사용하여 File 객체 생성
        File f = new File(uploadPath + newName);

        try{
            // 파일 업로드 진행
            mf.transferTo(f);
        }catch(Exception e){
            e.printStackTrace();
        }

        // DB 작업의 결과를 담는다.
        int result = qnaBoardService.postInsert(qto);

        model.addAttribute("result", result);

        return "/qna/writePro";
    }

    // list 에서 글제목을 클릭했을때, 작동
    @GetMapping("content")
    public String content( @RequestParam("postNo") int postNo, @ModelAttribute("pageNum") int pageNum, Model model ){
        
        QnaBoardDTO qto = qnaBoardService.postContent(postNo);
        model.addAttribute("qto", qto);

        return "/qna/content";
    }

    // content 에서 수정버튼을 클릭했을때, 작동
    @GetMapping("update")
    public String updateForm( @RequestParam("postNo") int postNo, @ModelAttribute("pageNum") int pageNum, Model model ){

        QnaBoardDTO qto = qnaBoardService.showContent(postNo);
        model.addAttribute("qto", qto);
        
        return "/qna/updateForm";
    }

    // updateForm 에서 수정 버튼을 클릭했을때, 작동
    @PostMapping("update")
    public String updatePro( @RequestParam("image") MultipartFile mf, @ModelAttribute("pageNum") int pageNum, QnaBoardDTO qto, Model model ){

        String newName = UUID.randomUUID().toString().replace("-", "") + mf.getOriginalFilename();
        String filePath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\qnaUpload\\";
        String originalFileName = mf.getOriginalFilename();
        String orgName = qto.getOriginalName();

        if(originalFileName != null){
            File f = new File(qto.getImgPath()+"/"+qto.getImgName());
            f.delete();

            qto.setImgName(newName);
            qto.setImgPath(filePath);
            qto.setOriginalName(originalFileName);
        } else {    // 입력받은 새로운 파일이 없다면,
            qto.setImgName(qto.getImgName());
            qto.setImgPath(qto.getImgPath());
            qto.setOriginalName(orgName);
        }

        int result = qnaBoardService.postUpdate(qto);
        model.addAttribute("result", result);

        return "qna/updatePro";
    }

    // delete
    @GetMapping("delete")
    public String delete( @ModelAttribute("pageNum") int pageNum, @RequestParam("postNo") int postNo, Model model ){
        QnaBoardDTO qto = qnaBoardService.showContent(postNo);

        String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\qnaUpload\\";
        String uploadName = qto.getImgName();

        File f = new File(uploadPath + uploadName);
        f.delete();

        qnaBoardService.postDelete(postNo);
        return "redirect:/qna/list";
    }

    // showRecord 팝업창 화면 구성
    @GetMapping("showRecord")
    public String showRecord( @RequestParam("nick") String nick, Model model ){
        // RecBoardDTO rto = qnaBoardService.recordSelect(nick);
        // model.addAttribute("rto", rto);
        return "/qna/record";
    }

    // ajax 로 넘어온 댓글 작성
    @PostMapping("replyInsert")
    @ResponseBody
    public String replyInsert( @RequestParam("postNo") int postNo, @RequestParam("writer") String writer, @RequestParam("content") String content ){

        String message = "댓글 작성에 실패하였습니다.";

        if( qnaBoardService.replyInsert(postNo, writer, content) == 1 ){
            message = "댓글 작성에 성공했습니다.";
        }

        return message;
    }

    // 댓글 리스트 출력
    @PostMapping("replyList")
    public String replyList( @RequestParam("postNo") int postNo, Model model ){

        List<QnaReplyDTO> list = qnaBoardService.replySelect(postNo);

        model.addAttribute("list", list);

        return "/qna/replyList";
    }

    // 답변 삭제
    @PostMapping("replyDelete")
    @ResponseBody
    public String replyDelete( @RequestParam("replyNo") int replyNo ){
        String result = "삭제를 실패하였습니다.";

        if( qnaBoardService.replyDelete(replyNo) == 1 ){
            result = "삭제를 완료하였습니다.";
        }

        return result;
    }
}

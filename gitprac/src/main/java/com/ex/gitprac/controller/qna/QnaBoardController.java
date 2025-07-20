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

import com.ex.gitprac.service.qna.QnaBoardService;

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

        return "/qna/listPrac3";
    }

    // 글작성 버튼을 눌렀을 때, 작동
    @GetMapping("write")
    public String write(){
        return "/qna/writeForm";
    }

    // 글작성 form 에서 업로드 버튼을 눌렀을 때, 작동
    @PostMapping("write")
    public String write( @RequestParam("image") MultipartFile mf, QnaBoardDTO qto, Model model){
        int result = 0;

        if( mf != null && !mf.isEmpty() ){
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
    
            
            try{
                // 경로와 파일명을 사용하여 File 객체 생성
                File f = new File(uploadPath + newName);
                // 파일 업로드 진행
                mf.transferTo(f);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        // DB 작업의 결과를 담는다.
        result = qnaBoardService.postInsert(qto);

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
        if( mf != null && !mf.isEmpty() ){
            // image 라는 file 타입의 input 태그에 들어온 값이 있다면
            String newName = UUID.randomUUID().toString().replace("-", "") + mf.getOriginalFilename();
            String filePath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\qnaUpload\\";
            String originalFileName = mf.getOriginalFilename();
            String imgWebPath = "/qnaUpload/";

            try{
                File f = new File(filePath + qto.getImgName());
                if( f.exists() ){
                    f.delete();
                }

                File nf = new File(filePath + newName);
                mf.transferTo(nf);
            }catch( Exception e ){
                e.printStackTrace();
            }

            qto.setImgName(newName);
            qto.setImgPath(imgWebPath);
            qto.setOriginalName(originalFileName);
        }

        int result = qnaBoardService.postUpdate(qto);
        model.addAttribute("result", result);
        model.addAttribute("qto", qto);

        return "qna/updatePro";
    }

    // delete
    @GetMapping("delete")
    public String delete( @ModelAttribute("pageNum") int pageNum, @RequestParam("postNo") int postNo, Model model ){
        QnaBoardDTO qto = qnaBoardService.showContent(postNo);

        String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\qnaUpload\\";
        String uploadName = qto.getImgName();

        try{
            File f = new File(uploadPath + uploadName);
            if( f.exists() ){
                f.delete();
            }
        }catch( Exception e ){
            e.printStackTrace();
        }

        int result = qnaBoardService.postDelete(postNo);

        model.addAttribute("result", result);
        return "/qna/deletePro";
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

    // 답변 삭제 after
    @PostMapping("replyDelete2")
    @ResponseBody
    public String replyDeleteNew( @RequestParam("replyNo") int replyNo ){
        String result = "삭제를 실패하였습니다.";

        if( qnaBoardService.replyDeleteNew(replyNo) == 1 ){
            result = "삭제를 완료하였습니다.";
        }

        return result;
    }

    // 검색창 구현                         // 이거 안씀
    @PostMapping("searchList")
    public String searchList( @RequestParam(name="pageNum", defaultValue="1") int pageNum, @RequestParam("category") String category, @RequestParam("keyword") String keyword, Model model ){
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = qnaBoardService.searchListCount(category, keyword);

        List<QnaBoardDTO> list = null;
        if( count > 0 ){
            list = qnaBoardService.searchBoardList(category, keyword, start, end);
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

        return "/qna/searchList";
    }

    // 답변 완료된 글목록 출력              // 이거 안씀
    @GetMapping("listChecked")
    public String searchList( @RequestParam(name="pageNum", defaultValue="1") int pageNum, Model model ){
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = qnaBoardService.searchListCountByIsAnsweredChecked();

        List<QnaBoardDTO> list = null;
        if( count > 0 ){
            list = qnaBoardService.isAnsweredStatusChecked(start, end);
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

        return "/qna/searchList";
    }

    // 검색 + 답변 여부 선택
    @PostMapping("searchList2")
    public String searchList2( @RequestParam(name="pageNum", defaultValue="1") int pageNum, @RequestParam("category") String category, @RequestParam("keyword") String keyword, @RequestParam("isAnswered") int isAnswered , Model model) {
        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;
        int count = qnaBoardService.searchListCount2(category, keyword, isAnswered);
        List<QnaBoardDTO> list = qnaBoardService.searchBoardList2(category, keyword, isAnswered, start, end);
        

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

        return "/qna/searchList";
    }


}

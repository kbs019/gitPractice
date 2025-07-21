package com.ex.gitprac.controller.info;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.data.info.InfoReplyDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.info.InfoBoardService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/info/")
@RequiredArgsConstructor
public class InfoController {

    private final InfoBoardService infoBoardService;

    // 정보 게시판 카테고리 글 리스트
    @GetMapping("list")
    public String listByCategory(   @RequestParam(name = "category", required = false, defaultValue = "전체글") String category,
                                    @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                    Model model) {

        int pageSize = 10;
        int currentPage = pageNum;
        int start = (currentPage - 1) * pageSize + 1;
        int end = currentPage * pageSize;

        int count;
        List<InfoBoardDTO> list = null;

        if ("전체글".equals(category) || "전체 글".equals(category)) {
            count = infoBoardService.count(); // 전체 게시글 수
            list = infoBoardService.infoBoardList(start, end); // 전체 리스트
        } else {
            count = infoBoardService.cateCount(category);
            list = (count > 0)
                ? infoBoardService.infoCateBoardList(category, start, end)
                : Collections.emptyList();
        }

        int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
        int startPage = (int) ((currentPage - 1) / 10) * 10 + 1;
        int pageBlock = 10;
        int endPage = startPage + pageBlock - 1;
        if (endPage > pageCount) endPage = pageCount;

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("count", count);
        model.addAttribute("list", list);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("startPage", startPage);
        model.addAttribute("pageBlock", pageBlock);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("category", category);

        return "info/infoList";
    }

    // 글 작성 버튼 누를시 작동
    @GetMapping("write")
    public String write() {
        return "info/infoWriteForm";
    }

    // 글작성 Form 눌렀을 시 실행
    @PostMapping("write")
    public String writePro( InfoBoardDTO idto, 
                            HttpSession session, 
                            Model model) {
        
        // UserDTO user = (UserDTO)session.getAttribute("users");
        // String writer = user.getNick();
        // idto.setWriter(writer);

        int result = infoBoardService.infoInsert(idto);
        model.addAttribute("result", result);
        return "info/infoWritePro";
    }

    // 게시판의 게시글 제목 클릭이 실행
    @GetMapping("content")
    public String content(  @RequestParam("postNo") int postNo, 
                            @ModelAttribute("pageNum") int pageNum, 
                            Model model) {
        infoBoardService.viewsUp(postNo);
        InfoBoardDTO idto = infoBoardService.InfopostContent(postNo);
        model.addAttribute("idto", idto);

        return "info/content";
    }

    @GetMapping("update")
    public String infoUpdateForm(   @RequestParam(name = "category") String category, 
                                    @ModelAttribute("pageNum") int pageNum,
                                    @RequestParam(name = "postNo") int postNo,
                                    Model model) {

        InfoBoardDTO idto = infoBoardService.InfopostContent(postNo);
        model.addAttribute("idto", idto);
        
        return "info/infoUpdateForm";
    }
    @PostMapping("update")
    public String infoUpdatePro(    @RequestParam("category") String category,
                                    @ModelAttribute("pageNum") int pageNum,
                                    InfoBoardDTO idto, Model model) {
        
        int result = infoBoardService.infoPostUpdate(idto);
        model.addAttribute("result", result);
        model.addAttribute("idto", idto);

        return "info/infoUpdatePro";
    }

    @GetMapping("delete")
    public String delete(   @ModelAttribute("pageNum") int pageNum, 
                            @RequestParam("postNo") int postNo, 
                            @RequestParam("category") String category,
                            Model model) {
        infoBoardService.infoPostDelete(postNo);
        return "redirect:/info/list";
    }

    @PostMapping("reply/add")
    @ResponseBody
    public String addReply(@RequestBody InfoReplyDTO dto, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("users");
        if (user == null) return "unauthorized";

        dto.setWriter(user.getNick());
        if (dto.getRef() == 0) dto.setRef(0);

        int result = infoBoardService.insertReply(dto);
        return result == 1 ? "success" : "fail";
    }

    @GetMapping("reply/list")
    @ResponseBody
    public List<InfoReplyDTO> getReplyList(@RequestParam("postNo") int postNo) {
        return infoBoardService.getReply(postNo);
    }

    @PutMapping("/reply/update")
    @ResponseBody
    public String updateReply(@RequestBody InfoReplyDTO replyDTO) {
        System.out.println(">>> 댓글 수정 요청: " + replyDTO);
        int result = infoBoardService.updateReply(replyDTO);
        return (result == 1) ? "success" : "fail";
    }

    @DeleteMapping("/reply/delete")
    @ResponseBody
    public String deleteRely(@RequestParam("replyNo") int replyNo) {
        int result = infoBoardService.deleteReply(replyNo);
        return result == 1 ? "success" : "fail"; 
    }

    @GetMapping("search")
    @ResponseBody
    public List<InfoBoardDTO> search(@RequestParam("option") String option, @RequestParam("keyword") String keyword) {
        return infoBoardService.searchByOption(option, keyword);
    }
    
}
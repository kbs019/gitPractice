package com.ex.gitprac.controller.info;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.info.InfoBoardService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/info/")
@RequiredArgsConstructor
public class InfoController {

    private final InfoBoardService infoBoardService;

    // @GetMapping("list")
    // public String infoList(Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum ) {

    //     int pageSize=10;
    //     int currentPage = pageNum;
    //     int start = (currentPage-1)*pageSize+1;
    //     int end = currentPage*pageSize;
    //     int count = infoBoardService.count();

    //     List<InfoBoardDTO> list = null;
    //     if( count > 0 ) {
    //         list = infoBoardService.infoBoardList(start, end);
    //     }else {
    //         list = Collections.EMPTY_LIST;
    //     }
    //     int pageCount = count/pageSize + (count%pageSize == 0 ? 0 : 1);
    //     int startPage = (int)((currentPage - 1)/10)*10+1;
    //     int pageBlock = 10;
    //     int endPage = startPage+pageBlock - 1;
    //     if(endPage > pageCount){
    //         endPage = pageCount;
    //     } 

    //     model.addAttribute("pageSize", pageSize);
    //     model.addAttribute("currentPage", currentPage);
    //     model.addAttribute("start", start);
    //     model.addAttribute("end", end);
    //     model.addAttribute("count", count);
    //     model.addAttribute("list", list);
    //     model.addAttribute("pageCount", pageCount);
    //     model.addAttribute("startPage", startPage);
    //     model.addAttribute("pageBlock", pageBlock);
    //     model.addAttribute("endPage", endPage); 
    //     model.addAttribute("pageNum", pageNum);

    //     return "info/infoList";
    // }

@GetMapping("list")
public String listByCategory(@RequestParam("category") String category,
                             @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                             Model model) {

    int pageSize = 10;
    int currentPage = pageNum;
    int start = (currentPage - 1) * pageSize + 1;
    int end = currentPage * pageSize;

    int count;
    List<InfoBoardDTO> list;

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

    @GetMapping("write")
    public String write() {
        return "info/infoWriteForm";
    }

    @PostMapping("write")
    public String writePro( InfoBoardDTO idto, HttpSession session, Model model) {
        
        // UserDTO user = (UserDTO)session.getAttribute("users");
        // String writer = user.getNick();
        // idto.setWriter(writer);

        int result = infoBoardService.infoInsert(idto);
        model.addAttribute("result", result);
        return "info/infoWritePro";
    }
}
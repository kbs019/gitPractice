package com.ex.gitprac.controller.diary;

import java.io.File;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ex.gitprac.data.diary.DiaryDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.diary.DiaryService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@RequestMapping("/diary/*")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("main")
    public String main(HttpSession session, Model model, @RequestParam(name="pageNum", defaultValue="1" )int pageNum) {
        
        UserDTO udto = (UserDTO)session.getAttribute("users");
        String writer = udto.getNick();
        int pageSize = 6;
		int currentPage = pageNum;
		int start = (currentPage - 1)*pageSize + 1;
		int end = currentPage * pageSize;
		int count = diaryService.countDiary(writer);
		
		List<DiaryDTO> list = null;
		if( count > 0) {
			list = diaryService.listDiary(start, end, writer);
		}else {
			list = Collections.EMPTY_LIST;
		}
		int pageCount = count/pageSize + (count%pageSize == 0 ? 0 : 1);
		int startPage = (int)((currentPage - 1)/10)*10 + 1;
		int pageBlock = 10;
		int endPage = startPage+pageBlock - 1 ;
		if(endPage > pageCount) {
			endPage = pageCount;
		}
		
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("startPage", startPage);
		model.addAttribute("pageBlock", pageBlock);
		model.addAttribute("endPage", endPage);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("count", count);
		model.addAttribute("list", list);
        
        return "diary/main";
    }

    @PostMapping("insert")
    public String insert(DiaryDTO ddto, HttpSession session, MultipartFile mf, @RequestParam("contetnt") String content, Model model){
        
        UserDTO udto = (UserDTO)session.getAttribute("users");
        ddto.setWriter(udto.getNick());

        String originFileName = mf.getOriginalFilename();

        String newName = UUID.randomUUID().toString().replace("-", "")+originFileName;

        String uploadPath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\diaryUpload\\";

        String imgWebPath = "/diaryUpload/";

        ddto.setImgName(newName);
        ddto.setImgPath(imgWebPath);
        ddto.setOriginalName(originFileName);

        File file = new File(uploadPath + newName);
        
        try {
            mf.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        int result = diaryService.insertDiary(ddto);

        model.addAttribute("result", result);
        return "diary/insert";
    }

    @GetMapping("list")
    public List list(Model model, @RequestParam(name="pageNum", defaultValue="1" )int pageNum) {
        
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        
        return list;
    }
    
    @PostMapping("count")
    public int countDiary(HttpSession session, Model model) {
        String writer = (String)session.getAttribute("nick");
        int result = diaryService.countDiary(writer);
        return result;
    }
    

    @PostMapping("content")
    public DiaryDTO contentDiary(@RequestParam("diaryNo") int diaryNo, Model model){

        DiaryDTO ddto = diaryService.contentDiary(diaryNo);
        return ddto;
    }

    @PostMapping("update")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
    
}

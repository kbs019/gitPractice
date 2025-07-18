package com.ex.gitprac.controller.diary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ex.gitprac.data.diary.DiaryDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.diary.DiaryService;

import jakarta.servlet.http.HttpServletRequest;
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
        if (writer == null) return "redirect:/user/login";

        int pageSize = 6;
		int currentPage = pageNum;
		int start = (currentPage - 1)*pageSize + 1;
		int end = currentPage * pageSize;
		int count = diaryService.countDiary(writer);
		
		List<DiaryDTO> list = null;
		if( count > 0) {
			list = diaryService.listDiary(writer, start, end);
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
    
    @PostMapping("/insert")
    @ResponseBody
    public String insertDiary(@RequestParam("uploadFile") MultipartFile uploadFile,
                              @RequestParam("content") String content,
                              HttpSession session,
                              HttpServletRequest request) {
        UserDTO udto = (UserDTO) session.getAttribute("users");
        String writer = udto.getNick();
        if (writer == null) return "nologin";
        if (uploadFile.isEmpty()) return "nofile";

        // 원본 파일명을 출력
        String originalName = uploadFile.getOriginalFilename();
        // 이미지 파일의 중복을 피하기 위해 새로운 이미지 파일의 이름 생성
        String newName = UUID.randomUUID().toString().replace("-", "")+originalName;
        // 현재 프로젝트 내의 이미지 파일을 저장할 폴더의 경로를 지정
        String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\diaryUpload\\";
        // 브라우저가 접근할 수 있는 URL 경로
        String imgWebPath = "/diaryUpload/";
        
        File folder = new File(uploadPath);
        if (!folder.exists()) {
            folder.mkdirs();  // 존재하지 않으면 폴더 생성
        }
        DiaryDTO ddto = new DiaryDTO();

        // 새로 생성된 이름과 경로를 qto 객체에 대입
        ddto.setOriginalName(originalName);
        ddto.setImgName(newName);
        ddto.setImgPath(imgWebPath);
        ddto.setWriter(writer);
        ddto.setContent(content);
        // 경로와 파일명을 사용하여 File 객체 생성
        File f = new File(uploadPath + newName);

        try{
            // 파일 업로드 진행
            uploadFile.transferTo(f);
        }catch(Exception e){
            e.printStackTrace();
        }
        diaryService.insertDiary(ddto);
            return "success";
    }

    
    @PostMapping("/delete")
    @ResponseBody
    public String deleteDiary(@RequestParam("diaryNo") int diaryNo) {
        diaryService.deleteDiary(diaryNo);
        return "deleted";
    }

    // 게시글 수정
    @PostMapping("/diary/update")
    public String updateDiary(@ModelAttribute DiaryDTO dto,
                          @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
                          Model model) {

    // 기존 이미지 정보를 hidden input으로부터 받아온다고 가정
    String existingImgName = dto.getImgName();
    String existingImgPath = dto.getImgPath();

    if (uploadFile == null || uploadFile.isEmpty()) {
        // 새 파일 업로드 없을 경우 기존 이미지 유지
        dto.setImgName(existingImgName);
        dto.setImgPath(existingImgPath);
    } else {
        // 새 파일 업로드 처리
        String fileName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString().replace("-", "")+fileName;
        String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\diaryUpload\\";
        String uploadDir = "/diaryUpload/"; // 실제 경로로 설정
        Path path = Paths.get(uploadDir, fileName);
        try {
            Files.copy(uploadFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            dto.setOriginalName(fileName);
            dto.setImgName(newName);
            dto.setImgPath(uploadDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    diaryService.updateDiary(dto); // 서비스 호출
    return "redirect:/diary/main";
}

    
}

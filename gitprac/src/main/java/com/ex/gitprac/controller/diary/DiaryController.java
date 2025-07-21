package com.ex.gitprac.controller.diary;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ex.gitprac.data.diary.DiaryDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.diary.DiaryService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;




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
    
    @PostMapping("insert")
    @ResponseBody
    public String insertDiary(@RequestParam("uploadFile") MultipartFile uploadFile,
                              @RequestParam("content") String content,
                              HttpSession session) {
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
    
    @PostMapping("delete")
    @ResponseBody
    public String deleteDiary(@RequestParam("diaryNo") int diaryNo) {
        
        DiaryDTO dto = diaryService.getDiary(diaryNo);

        String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\diaryUpload\\";
        String uploadName = dto.getImgName();

        try{
            File f = new File(uploadPath + uploadName);
            if( f.exists() ){
                f.delete();
            }
        }catch( Exception e ){
            e.printStackTrace();
        }

        diaryService.deleteDiary(diaryNo);
        return "deleted";
    }

    // 게시글 수정
    @PostMapping("update")
    @ResponseBody
    public String updateDiary(@RequestParam("uploadFile") MultipartFile uploadFile,
                              @RequestParam("content") String content,
                              @RequestParam("diaryNo") int diaryNo,
                              HttpSession session) {
        DiaryDTO dto = diaryService.getDiary(diaryNo);

        if (uploadFile == null || uploadFile.isEmpty()) {

            String originalName = dto.getOriginalName();
            String newName = dto.getImgName();
            String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\diaryUpload\\";
            String imgWebPath = "/diaryUpload/";

            File folder = new File(uploadPath);
            if (!folder.exists()) {
                folder.mkdirs();  // 존재하지 않으면 폴더 생성
            }
            dto.setContent(content);
            dto.setImgName(newName);
            dto.setImgPath(imgWebPath);
            dto.setOriginalName(originalName);

        } else{

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
            try{
                File f = new File(uploadPath + dto.getImgName());
                if( f.exists() ){
                    f.delete();
                }
            }catch( Exception e ){
                e.printStackTrace();
            }
            
            // 새로 생성된 이름과 경로를 qto 객체에 대입
            dto.setOriginalName(originalName);
            dto.setImgName(newName);
            dto.setImgPath(imgWebPath);
            dto.setContent(content);
            // 경로와 파일명을 사용하여 File 객체 생성
            File f = new File(uploadPath + newName);

            try{
                // 파일 업로드 진행
                uploadFile.transferTo(f);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        diaryService.updateDiary(dto);
        return "success";
    }
}
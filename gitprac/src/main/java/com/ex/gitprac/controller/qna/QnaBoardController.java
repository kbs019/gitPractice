package com.ex.gitprac.controller.qna;

import java.util.UUID;
import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ex.gitprac.data.qna.QnaBoardDTO;
import com.ex.gitprac.data.user.UserDTO;

import com.ex.gitprac.service.qna.QnaBoardService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/qna/*")
@RequiredArgsConstructor
public class QnaBoardController {

    private final QnaBoardService qnaBoardService;

    @GetMapping("write")
    public String write(){
        return "/qna/writeForm";
    }

    @PostMapping("write")
    public String write( @RequestParam("image") MultipartFile mf, QnaBoardDTO qto, Model model, HttpSession session ){
        // 세션에 저장된 user 객체 꺼내기
        UserDTO user = (UserDTO) session.getAttribute("user");
        // user 객체의 nick 컬럼의 값을 꺼내어 writer 에 대입
        String writer = user.getNick();
        // writer 를 qto 객체에 저장
        qto.setWriter(writer);

        // 이미지 파일의 중복을 피하기 위해 새로운 이미지 파일의 이름 생성
        String newName = UUID.randomUUID().toString().replace("-", "")+mf.getOriginalFilename();
        // 현재 프로젝트 내의 이미지 파일을 저장할 폴더의 경로를 지정
        String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\qnaUpload\\";
        
        // 새로 생성된 이름과 경로를 qto 객체에 대입
        qto.setImgName(newName);
        qto.setImgPath(uploadPath);

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
}

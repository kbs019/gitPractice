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

import com.ex.gitprac.data.qna.QnaBoardDTO;

import com.ex.gitprac.service.qna.QnaBoardService;

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
    public String write( @RequestParam("image") MultipartFile mf, QnaBoardDTO qto, Model model ){

        String newName = UUID.randomUUID().toString().replace("-", "")+mf.getOriginalFilename();

        String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\qnaUpload\\";
        
        qto.setImgName(newName);
        qto.setImgPath(uploadPath);

        File f = new File(uploadPath + newName);

        try{
            mf.transferTo(f);
        }catch(Exception e){
            e.printStackTrace();
        }

        int result = qnaBoardService.postInsert(qto);

        model.addAttribute("result", result);

        return "/qna/writePro";
    }
}

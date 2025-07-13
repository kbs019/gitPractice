package com.ex.gitprac.controller.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.gitprac.data.qna.QnaBoardDTO;

//import com.ex.gitprac.service.qna.QnaBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/qna/*")
@RequiredArgsConstructor
public class QnaBoardController {

    //private final QnaBoardService qnaBoardService;

    @GetMapping("write")
    public String write(){
        return "/qna/writeForm";
    }

    @PostMapping("write")
    public String write( QnaBoardDTO qto, Model model ){
        return "/qna/writePro";
    }
}

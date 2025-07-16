package com.ex.gitprac.controller.diary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/diary/*")
@RequiredArgsConstructor
public class DiaryController {

    @GetMapping("main")
    public String main(){
        return "diary/main";
    }
}

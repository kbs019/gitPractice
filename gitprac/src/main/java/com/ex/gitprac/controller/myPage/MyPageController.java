package com.ex.gitprac.controller.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/*")
public class MyPageController {

    @GetMapping("main")
    public String main() {
        return "myPage/myPage";
    }

    
    
}

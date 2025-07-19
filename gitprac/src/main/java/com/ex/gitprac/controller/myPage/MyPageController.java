package com.ex.gitprac.controller.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/myPage/*")
public class MyPageController {

    @GetMapping("main")
    public String main() {
        return "myPage/myPage";
    }
}

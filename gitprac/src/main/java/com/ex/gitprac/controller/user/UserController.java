package com.ex.gitprac.controller.user;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UserController {
    //private final UserService userService;

    @GetMapping("main")
    public String main() {
        return "user/main";
    }
    
}

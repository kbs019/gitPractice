package com.ex.gitprac.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UserController {
    //private final UserService userService;

    @GetMapping("login")
    public String loginString() {
        return "user/loginForm";
    }
    @PostMapping("login")
    public String loginForm( @RequestParam("role") int role, HttpSession session, Model model) {

        model.addAttribute("role", role);

        return "user/loginPro";
    } 
      
    @GetMapping("insert")
    public String insert() {
        return "user/insertForm";
    }
}

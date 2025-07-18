package com.ex.gitprac.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.user.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;



@Controller
@RequestMapping("/user/*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("login")
    public String loginString() {
        return "user/loginForm";
    }
    @PostMapping("login")
    public String loginForm( @RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session ) {

        UserDTO udto = userService.loginCheck(id, pw);
        if( udto != null ) {
            session.setAttribute("users", udto);
        }
        return "user/loginPro";
    } 

    @GetMapping("logout")
    public String logout( HttpSession session ) {
        session.invalidate();
        return "user/logout";
    }
    
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
      
    @GetMapping("insert")
    public String userInsert() {
        return "user/insertForm";
    }

    @PostMapping("insert")
    public String userInsert(UserDTO udto, Model model) {

        int result = userService.userInsert(udto);
        model.addAttribute("result", result);
        return "user/insertPro";
    }
    
}

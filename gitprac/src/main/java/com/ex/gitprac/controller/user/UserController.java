package com.ex.gitprac.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String loginForm(    @RequestParam("id") String id, 
                                @RequestParam("pw") String pw,  
                                HttpSession session, Model model) {

        UserDTO udto = userService.loginCheck(id, pw);
        if( udto != null ) {
            session.setAttribute("users", udto);
            model.addAttribute("result", 1);
        }else {
            model.addAttribute("result", 0);
        }
        return "user/loginPro";
    } 

    @GetMapping("logout")
    public String logout( HttpSession session ) {
        session.invalidate();
        return "redirect:/user/login";          // 로그아웃 시, URI 에 user/login 나오도록 처리
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

    @PostMapping("loginCheckByBannedUntil")
    public String loginCheckByBannedUntil( @RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session, Model model ){
        UserDTO user = userService.loginCheckByBannedUntil(id, pw);

        if( user == null ){
            model.addAttribute("result", 0);    // 로그인 실패
        } else if ( user.getStatus() == 1 ){
            model.addAttribute("result", -1);
            model.addAttribute("message", user.getBannedUntil() + "까지 로그인이 제한되어있습니다.");
        } else if( user.getStatus() == 2 ){
            model.addAttribute("result", 2);
            model.addAttribute("message", "탈퇴되어있는 계정입니다.");
        } else {
            session.setAttribute("users", user);
            model.addAttribute("result", 1);
        }

        return "user/loginPro2";
    }

    @PostMapping("findId")
    @ResponseBody
    public String FindId(@RequestParam("email") String email) {
        UserDTO user = userService.findIdByEmail(email);
        if( user != null ) {
            return user.getId();
        } else {
            return "notfound";
        }
    }

    @PostMapping("FindPw")
    @ResponseBody
    public String ajaxFindPw(@RequestParam("id") String id, @RequestParam("email") String email) {
        String tempPw = userService.findPwAndSendTempPw(id, email);
        if (tempPw != null) {
            return "success";
        } else {
            return "fail";
        }
    }
}

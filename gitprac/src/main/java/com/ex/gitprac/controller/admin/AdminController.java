package com.ex.gitprac.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ex.gitprac.service.admin.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/*")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("adminPage")
    public String adminPage(){
        return "/admin/adminPage";
    }
}

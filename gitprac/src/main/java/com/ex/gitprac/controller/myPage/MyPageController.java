package com.ex.gitprac.controller.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ex.gitprac.data.pet.PetDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.myPage.MyPageService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
@RequestMapping("/myPage/*")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("main")
    public String main() {
        return "myPage/myPage";
    }

    @PostMapping("updateUser")
    public String updateUser(UserDTO udto, HttpSession session) {
                
        String result = "error";
        
        UserDTO dto = (UserDTO) session.getAttribute("users");
        
        dto.setPw(udto.getPw());
        dto.setNick(udto.getNick());
        dto.setEmail(udto.getEmail());
        dto.setPhone(udto.getPhone());
        dto.setCarrier(udto.getCarrier());

        int ok = myPageService.updateUser(dto);
        if(ok == 1){
            result = "success";
        }

        return result;
    }

    @PostMapping("updatePet")
    public String updatePet(@RequestParam("petNo") int petNo, PetDTO pdto, HttpSession session) {
                
        String result = "error";
        
        PetDTO dto = myPageService.getPet(petNo);
        
        dto.setPetName(pdto.getPetName());
        dto.setPetBreed(pdto.getPetBreed());
        dto.setPetAge(pdto.getPetAge());
        dto.setPetSize(pdto.getPetSize());
        dto.setPetWeight(pdto.getPetWeight());
        dto.setPetBirth(pdto.getPetBirth());

        int ok = myPageService.updatePet(dto);
        if(ok == 1){
            result = "success";
        }

        return result;
    }
}

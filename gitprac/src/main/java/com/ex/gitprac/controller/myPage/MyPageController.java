package com.ex.gitprac.controller.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.gitprac.data.pet.PetDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.myPage.MyPageService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;





@Controller
@RequiredArgsConstructor
@RequestMapping("/myPage/*")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("main")
    public String main() {
        return "myPage/myPage";
    }

    @PostMapping("pwCheck")
    public String pwCheck(@RequestParam("pw") String pw, HttpSession session) {
        
        String result = "error";
        UserDTO udto = (UserDTO)session.getAttribute("users");
        String id = udto.getId();
        int ok = myPageService.pwCheck(id, pw);
        if(ok == 1){
            result = "success";
        }
        return result;
    }

    @PostMapping("getUser")
    @ResponseBody
    public UserDTO getUser(HttpSession session) {
        UserDTO dto = (UserDTO)session.getAttribute("users");
        String id = dto.getId();
        
        return myPageService.getUser(id);
    }

    @PostMapping("getPet")
    public String getPet() {
        String result = "error";


        int ok = 0;

        if(ok == 1){
            result = "success";
        }
        return result;
    }
    
    
    
    @PostMapping("updateUser")
    public String updateUser(UserDTO udto, HttpSession session) {
                
        String result = "error";
        
        UserDTO dto = (UserDTO) session.getAttribute("users");
        String pastWriter = dto.getNick();
        dto.setPw(udto.getPw());
        dto.setNick(udto.getNick());
        dto.setEmail(udto.getEmail());
        dto.setPhone(udto.getPhone());
        dto.setCarrier(udto.getCarrier());

        int ok = myPageService.updateUser(dto);
        myPageService.changeWriter(dto, pastWriter);
        if(ok == 1){
            result = "success";
        }
        return result;
    }

    @PostMapping("deleteUser")
    public String deleteUser(HttpSession session){

        String result = "error";
        UserDTO dto = (UserDTO)session.getAttribute("users");
        String id = dto.getId();

        int ok = myPageService.deleteUser(id);
        if(ok == 1){
            result = "success";
        }
        return result;
    }

    @PostMapping("insertPet")
    public String insertPet(PetDTO pdto){

        String result = "error";
        
        int ok = myPageService.insertPet(pdto);
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

    @PostMapping("deletePet")
    public String deletePet(PetDTO pdto){
        
        String result = "error";
        int petNo = pdto.getPetNo();
        
        int ok1 = myPageService.deletePet(petNo);
        myPageService.deletePetRec(petNo);
        if(ok1 == 1){
            result = "success";
        }
        return result;
    }
}

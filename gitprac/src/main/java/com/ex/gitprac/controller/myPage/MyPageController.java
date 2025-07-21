package com.ex.gitprac.controller.myPage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ex.gitprac.data.ask.AskDTO;
import com.ex.gitprac.data.diary.DiaryDTO;
import com.ex.gitprac.data.info.InfoBoardDTO;
import com.ex.gitprac.data.pet.PetDTO;
import com.ex.gitprac.data.qna.QnaBoardDTO;
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

    @PostMapping("listPet")
    @ResponseBody
    public List<PetDTO> listPet(@RequestParam("id") String id) {

        return myPageService.listPet(id);
    }
        
    @PostMapping("updateUser")
    @ResponseBody
    public String updateUser(UserDTO udto, HttpSession session) {
                
        String result = "error";
        
        UserDTO dto = (UserDTO) session.getAttribute("users");
        String pastWriter = dto.getNick();
        dto.setPw(udto.getPw());
        String newWriter = udto.getNick();
        dto.setNick(newWriter);
        dto.setEmail(udto.getEmail());
        dto.setPhone(udto.getPhone());
        dto.setCarrier(udto.getCarrier());

        myPageService.changeWriter(newWriter, pastWriter);
        int ok = myPageService.updateUser(dto);
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
    @ResponseBody
    public String insertPet(@RequestBody PetDTO pdto, HttpSession session){

        String result = "error";
        UserDTO dto = (UserDTO)session.getAttribute("users");
        pdto.setId(dto.getId());
        int ok = myPageService.insertPet(pdto);
        if(ok == 1){
            result = "success";
        }
        return result;
    }

    @PostMapping("updatePet")
    @ResponseBody
    public String updatePet(@RequestParam("petNo") int petNo, @RequestBody PetDTO pdto, HttpSession session) {
                
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
    
    @GetMapping("/listAsk")
    @ResponseBody
    public Map<String, Object> listAsk(@RequestParam int page, HttpSession session) {
        String id = ((UserDTO) session.getAttribute("users")).getId();
        int total = myPageService.countAsk(id);
        List<AskDTO> list = myPageService.listAsk(id, page);
        return Map.of("list", list, "total", total);
    }

    @GetMapping("/listDiary")
    @ResponseBody
    public Map<String, Object> listDiary(@RequestParam int page, HttpSession session) {
        String id = ((UserDTO) session.getAttribute("users")).getId();
        int total = myPageService.countDiary(id);
        List<DiaryDTO> list = myPageService.listDiary(id, page);
        return Map.of("list", list, "total", total);
    }

    @GetMapping("/listInfo")
    @ResponseBody
    public Map<String, Object> listInfo(@RequestParam int page, HttpSession session) {
        String id = ((UserDTO) session.getAttribute("users")).getId();
        int total = myPageService.countInfo(id);
        List<InfoBoardDTO> list = myPageService.listInfo(id, page);
        return Map.of("list", list, "total", total);
    }

    @GetMapping("/listQna")
    @ResponseBody
    public Map<String, Object> listQna(@RequestParam int page, HttpSession session) {
        String id = ((UserDTO) session.getAttribute("users")).getId();
        int total = myPageService.countQna(id);
        List<QnaBoardDTO> list = myPageService.listQna(id, page);
        return Map.of("list", list, "total", total);
    }

}

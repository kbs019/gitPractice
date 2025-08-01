package com.ex.gitprac.controller.myPage;

import java.util.HashMap;
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
    @ResponseBody
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
    @ResponseBody
    public PetDTO getPet(@RequestParam("petNo") int petNo){

        return myPageService.getPet(petNo);
    }

    @PostMapping("listPet")
    @ResponseBody
    public List<PetDTO> listPet(@RequestParam("id") String id) {

        return myPageService.listPet(id);
    }
     
    @PostMapping("/updateUser")
    @ResponseBody
    public String updateUser(@RequestBody UserDTO udto, HttpSession session) {
        String result = "error";
        
        UserDTO dto = (UserDTO) session.getAttribute("users");
        String pastWriter = dto.getNick();
        dto.setPw(udto.getPw());
        String newWriter = udto.getNick();
        dto.setNick(newWriter);
        dto.setEmail(udto.getEmail());
        dto.setPhone(udto.getPhone());
        dto.setCarrier(udto.getCarrier());
        // 세션 값도 갱신
        dto.setNick(newWriter);
        dto.setEmail(udto.getEmail());
        dto.setPhone(udto.getPhone());
        dto.setCarrier(udto.getCarrier());
        if (!pastWriter.equals(newWriter)) {
            myPageService.changeWriter(newWriter, pastWriter);
        }
        int ok = myPageService.updateUser(dto);
        if(ok == 1){
            result = "success";
        }
        return result;
    }


    @PostMapping("deleteUser")
    @ResponseBody
    public String deleteUser(HttpSession session){

        String result = "error";
        UserDTO dto = (UserDTO)session.getAttribute("users");
        String id = dto.getId();

        int ok = myPageService.deleteUser(id);
        if(ok == 1){
            session.invalidate();
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
    public String updatePet(@RequestBody PetDTO pdto) {
                
        String result = "error";
        int petNo = pdto.getPetNo();
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
    @ResponseBody
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
    public Map<String, Object> listAsk(@RequestParam("page") int page, HttpSession session) {
        String writer = ((UserDTO) session.getAttribute("users")).getNick();
        int start = (page - 1) * 10 + 1;
        int end = page * 10;

        Map<String, Object> param = new HashMap<>();
        param.put("writer", writer);
        param.put("start", start);
        param.put("end", end);

        List<AskDTO> list = myPageService.listAsk(param);
        int total = myPageService.countAsk(writer);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }

    @GetMapping("/listInfo")
    @ResponseBody
    public Map<String, Object> listInfo(@RequestParam("page") int page, HttpSession session) {
        String writer = ((UserDTO) session.getAttribute("users")).getNick();
        int start = (page - 1) * 10 + 1;
        int end = page * 10;

        Map<String, Object> param = new HashMap<>();
        param.put("writer", writer);
        param.put("start", start);
        param.put("end", end);

        List<InfoBoardDTO> list = myPageService.listInfo(param);
        int total = myPageService.countInfo(writer);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }

    @GetMapping("/listQna")
    @ResponseBody
    public Map<String, Object> listQna(@RequestParam("page") int page, HttpSession session) {
        String writer = ((UserDTO) session.getAttribute("users")).getNick();
        int start = (page - 1) * 10 + 1;
        int end = page * 10;

        Map<String, Object> param = new HashMap<>();
        param.put("writer", writer);
        param.put("start", start);
        param.put("end", end);

        List<QnaBoardDTO> list = myPageService.listQna(param);
        int total = myPageService.countQna(writer);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
}

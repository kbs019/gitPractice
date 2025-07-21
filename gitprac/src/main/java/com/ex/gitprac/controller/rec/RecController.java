package com.ex.gitprac.controller.rec;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

import com.ex.gitprac.data.pet.PetDTO;
import com.ex.gitprac.data.rec.RecDTO;
import com.ex.gitprac.data.user.UserDTO;
import com.ex.gitprac.service.rec.RecService;
import com.ex.gitprac.service.pet.PetService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/rec")
@RequiredArgsConstructor
public class RecController {

    private final PetService petService;
    private final RecService recService;

    /**
     * 🗂 일지 목록 조회 (필터 포함 + 전체 초기화)
     */
    @GetMapping("")
public String recListPage(
    @RequestParam(name = "petNo", required = false) Integer petNo,
    @RequestParam(name = "startDate", required = false) String startDate,
    @RequestParam(name = "endDate", required = false) String endDate,
    @RequestParam(name = "categoryGroup", required = false) String categoryGroup,
    @RequestParam(name = "reset", required = false) String reset,
    HttpSession session,
    HttpServletResponse response,
    Model model
) throws IOException {
    // 로그인한 사용자 ID (작성자)
    UserDTO users = (UserDTO) session.getAttribute("users");

    if (users == null) {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('로그인 후 이용 가능합니다.'); window.location.replace('/user/login');</script>");
        out.flush();
        return null;
    }

    String writer = users.getId();
    int offset = 0;
    int limit = 15;
    List<RecDTO> recList = null; // 기본값: 아무 것도 안 보이게

    // 🐶 펫 목록 조회 (드롭다운 표시용)
    List<PetDTO> petList = petService.getPetsByUserId(writer);
    model.addAttribute("petList", petList);

    // 🟡 petNo가 null이면 아무것도 보여주지 않음
    if (petNo != null) {
        if ("true".equals(reset)) {
            recList = recService.getRecListWithPaging(writer, offset, limit);
            startDate = "";
            endDate = "";
            categoryGroup = "";
        } else {
            if (startDate == null || startDate.isBlank()) {
                startDate = "1900-01-01";
            }
            if (endDate == null || endDate.isBlank()) {
                endDate = "2100-12-31";
            }

            recList = recService.getRecListFilteredWithPaging(
                writer, petNo, startDate, endDate, categoryGroup, offset, limit);
        }
    }

    // 모델에 전달
    model.addAttribute("recList", recList);
    model.addAttribute("startDate", startDate != null && startDate.equals("1900-01-01") ? "" : startDate);
    model.addAttribute("endDate", endDate != null && endDate.equals("2100-12-31") ? "" : endDate);
    model.addAttribute("categoryGroup", categoryGroup);
    model.addAttribute("selectedPetNo", petNo); // 선택값 유지

    return "rec/list";
}



    /**
     * 📝 일지 작성 폼 페이지
     */
    @GetMapping("/upload")
    public String uploadPage(HttpSession session, Model model) {
    UserDTO users = (UserDTO) session.getAttribute("users");
    if (users == null) {
        return "redirect:/user/login";
    }

    String userId = users.getId();
    List<PetDTO> petList = petService.getPetsByUserId(userId);
    
    model.addAttribute("petList", petList);
    model.addAttribute("rec", new RecDTO()); // 바인딩 객체도 함께 전달 (폼 입력용)

    return "rec/upload";
    }

    /**
     * ✅ 일지 등록 처리 + 이미지 저장
     */
    @PostMapping("/upload")
    public String saveRec(@RequestParam("image") MultipartFile mf,
                        RecDTO rto,
                        HttpSession session,
                        Model model,
                        RedirectAttributes redirectAttributes) {

        // 로그인한 사용자 정보 가져오기
        UserDTO users = (UserDTO) session.getAttribute("users");
        rto.setWriter(users.getId()); // 작성자 정보 세팅

        // 업로드된 이미지가 있을 경우만 처리
        if (!mf.isEmpty()) {
            try {
                // 원본 파일명
                String orgImgName = mf.getOriginalFilename();

                // 고유 파일명 (UUID + 원본명)
                String imgName = UUID.randomUUID().toString().replace("-", "") + orgImgName;

                // 저장 경로 설정
                String uploadPath = new File("").getAbsolutePath() + "\\src\\main\\resources\\static\\recUpload\\";
                Path savePath = Paths.get(uploadPath, imgName);

                // 실제 저장
                mf.transferTo(savePath.toFile());

                // DTO에 이미지 정보 세팅
                rto.setOrgImgName(orgImgName);
                rto.setImgName(imgName);
                rto.setImgPath("/recUpload/");

            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("msg", "이미지 업로드 실패");
                return "redirect:/rec/upload";
            }
        }

        // DB 저장
        recService.save(rto);
        redirectAttributes.addFlashAttribute("msg", "일지 등록 완료!");
        return "redirect:/rec";
    }




    /**
     * 📄 일지 상세 페이지
     */
    @GetMapping("/content/{recNo}")
    public String recContentPage(@PathVariable("recNo") int recNo, Model model) {
        RecDTO rto = recService.getRecByNo(recNo);
        model.addAttribute("rto", rto);
        return "rec/content";
    }

    @GetMapping("/edit/{recNo}")
public String editRecForm(@PathVariable("recNo") Integer recNo,
                          HttpSession session,
                          HttpServletResponse response,
                          Model model) throws IOException {

    // 세션에서 사용자 정보 가져오기
    UserDTO users = (UserDTO) session.getAttribute("users");

    // 로그인 여부 확인
    if (users == null) {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('로그인 후 이용 가능합니다.'); window.location.replace('/user/login');</script>");
        out.flush();
        return null;
    }

    // 기존 일지 정보
    RecDTO rec = recService.getRecByNo(recNo);

    // 사용자 ID로 반려견 리스트 가져오기
    List<PetDTO> petList = petService.getPetsByUserId(users.getId());

    // 모델에 담기
    model.addAttribute("rec", rec);
    model.addAttribute("petList", petList);

    return "rec/edit";
}


    @PostMapping("/edit")
    public String editRecSubmit(@ModelAttribute RecDTO rec, RedirectAttributes redirectAttributes) {
        recService.updateRec(rec);
        redirectAttributes.addAttribute("recNo", rec.getRecNo());
        return "redirect:/rec/content/{recNo}";
    }


    @GetMapping("/delete/{recNo}")
    public String deleteRec(@PathVariable("recNo") Integer recNo) {
        recService.deleteRec(recNo);
        return "redirect:/rec";
    }

    @GetMapping("/more")
    @ResponseBody
    public List<RecDTO> loadMoreRecs(@RequestParam(name = "offset") int offset,
                                    @RequestParam(name = "limit") int limit,
                                    HttpSession session) {
        UserDTO users = (UserDTO) session.getAttribute("users");
        String writer = users.getId();
        return recService.getRecListWithPaging(writer, offset, limit);
    }
}

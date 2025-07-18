package com.ex.gitprac.controller.rec;

import java.io.File;
import java.nio.file.Files;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

import com.ex.gitprac.data.rec.RecDTO;
import com.ex.gitprac.service.rec.RecService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/rec")
@RequiredArgsConstructor
public class RecController {

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
        Model model
    ) {
        List<RecDTO> recList;

        if ("true".equals(reset)) {
            recList = recService.getAllRecs();
            startDate = "";
            endDate = "";
            categoryGroup = "";
        } else {
            recList = recService.getFilteredRecs(petNo, startDate, endDate, categoryGroup);
        }

        model.addAttribute("recList", recList);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("categoryGroup", categoryGroup);

        return "rec/list";
    }

    /**
     * 📝 일지 작성 폼 페이지
     */
    @GetMapping("/upload")
    public String uploadPage() {
        return "rec/upload";
    }

    /**
     * ✅ 일지 등록 처리 + 이미지 저장
     */
    @PostMapping("/upload")
public String saveRec(
    @RequestParam("image") MultipartFile mf,
    RecDTO rto,
    Model model,
    RedirectAttributes redirectAttributes
) {
    // 업로드된 이미지가 있을 경우만 처리
    if (!mf.isEmpty()) {
        try {
            // 원본 파일명
            String orgImgName = mf.getOriginalFilename();

            // 고유 파일명 (UUID + 확장자)
            // String ext = orgImgName.substring(orgImgName.lastIndexOf("."));
            String imgName = UUID.randomUUID().toString().replace("-", "") + orgImgName;

            // 저장 폴더 경로
            // String uploadDir = "D:" + File.separator +
            //                     "oner" + File.separator +
            //                     "VisualStudio" + File.separator +
            //                     "githurb" + File.separator +
            //                     "gitPractice" + File.separator +
            //                     "gitprac" + File.separator +
            //                     "src" + File.separator +
            //                     "main" + File.separator +
            //                     "resources" + File.separator +
            //                     "static" + File.separator +
            //                     "recUpload";
            String imgPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\recUpload\\";

            // 폴더 없으면 생성
            // File uploadPath = new File(uploadDir);
            // if (!uploadPath.exists()) {
            //     uploadPath.mkdirs();
            // }

            // 실제 저장
            File fileToSave = new File(imgPath + imgName);
            mf.transferTo(fileToSave);

            // DTO에 정보 세팅
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
}

package com.ex.gitprac.controller.rec;

import java.io.File;
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

import com.ex.gitprac.data.rec.RecDTO;
import com.ex.gitprac.service.rec.RecService;

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
        int offset = 0;
        int limit = 15;
        List<RecDTO> recList;

        if ("true".equals(reset)) {
            recList = recService.getRecListWithPaging(offset, limit);
            startDate = "";
            endDate = "";
            categoryGroup = "";
        } else {
            // 기본 날짜 범위 설정
            if (startDate == null || startDate.isBlank()) {
                startDate = "1900-01-01";
            }
            if (endDate == null || endDate.isBlank()) {
                endDate = "2100-12-31";
            }

            recList = recService.getRecListFilteredWithPaging(petNo, startDate, endDate, categoryGroup, offset, limit);
        }

        // model에 데이터 전달
        model.addAttribute("recList", recList);
        model.addAttribute("startDate", startDate.equals("1900-01-01") ? "" : startDate);
        model.addAttribute("endDate", endDate.equals("2100-12-31") ? "" : endDate);
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
            String uploadPath = new File("").getAbsolutePath()+"\\src\\main\\resources\\static\\recUpload\\";

            // 실제 저장
            // File fileToSave = new File(uploadPath + imgName);
            // mf.transferTo(fileToSave);
            Path savePath = Paths.get(uploadPath, imgName);
            mf.transferTo(savePath.toFile());

            // DTO에 정보 세팅
            rto.setOrgImgName(orgImgName);
            rto.setImgName(imgName);
            rto.setImgPath("/recUpload/");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "이미지 업로드 실패");
            return "redirect:/rec/upload";
        }
            // DB 저장
        recService.save(rto);
        redirectAttributes.addFlashAttribute("msg", "일지 등록 완료!");
        return "redirect:/rec";
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
    public String editRecForm(@PathVariable("recNo") Integer recNo, Model model) {
        System.out.println(">> editRecForm() 호출됨: " + recNo);    // 호출됐는지 확인용
        RecDTO rec = recService.getRecByNo(recNo);
        model.addAttribute("rec", rec);
        return "rec/edit";
    }

    @PostMapping("/edit")
    public String editRecSubmit(@ModelAttribute RecDTO recDTO) {
        recService.updateRec(recDTO);
        return "redirect:/rec/content/" + recDTO.getRecNo();
    }

    @GetMapping("/delete/{recNo}")
    public String deleteRec(@PathVariable("recNo") Integer recNo) {
        recService.deleteRec(recNo);
        return "redirect:/rec";
    }

    @GetMapping("/more")
    @ResponseBody
    public List<RecDTO> loadMoreRecs(@RequestParam int offset, @RequestParam int limit) {
        return recService.getRecListWithPaging(offset, limit);
    }
}

package com.ex.gitprac.controller.rec;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.ex.gitprac.data.rec.RecDTO;
import com.ex.gitprac.service.rec.RecService;

@Controller
@RequestMapping("/rec/*")
@RequiredArgsConstructor
public class RecController {

    private final RecService recService;

    /**
     * 🗂 일지 목록 조회 (필터 포함)
     */
    @GetMapping("/rec")
    public String recListPage(
        @RequestParam(name = "petNo", required = false) Integer petNo,
        @RequestParam(name = "startDate", required = false) String startDate,
        @RequestParam(name = "endDate", required = false) String endDate,
        @RequestParam(name = "categoryGroup", required = false) String categoryGroup,
        Model model
    ) {
        List<RecDTO> recList = recService.getFilteredRecs(petNo, startDate, endDate, categoryGroup);
        model.addAttribute("recList", recList);
        return "rec/list";
    }


    /**
     * 📝 일지 작성 폼 페이지
     */
    @GetMapping("/rec/upload")
    public String uploadPage() {
        return "rec/upload";
    }

    /**
     * ✅ 일지 등록 처리
     */
    @PostMapping("/rec/upload")
    public String saveRec(
        @ModelAttribute RecDTO recDTO,
        RedirectAttributes redirectAttributes
    ) {
        recService.save(recDTO);
        redirectAttributes.addFlashAttribute("msg", "일지 등록 완료!");
        return "redirect:/rec";
    }
}

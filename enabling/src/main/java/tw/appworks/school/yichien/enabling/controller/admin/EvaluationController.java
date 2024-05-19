package tw.appworks.school.yichien.enabling.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.NewEvaluationForm;
import tw.appworks.school.yichien.enabling.dto.form.ReserveEvaluationForm;
import tw.appworks.school.yichien.enabling.service.AdminService;
import tw.appworks.school.yichien.enabling.service.webpage.EvaluationService;

@Controller
@RequestMapping("/admin/{domain}/setting/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;

    private final AdminService adminService;

    @Value("${prefix.domain}")
    private String domainPrefix;

    public EvaluationController(EvaluationService evaluationService, AdminService adminService) {
        this.evaluationService = evaluationService;
        this.adminService = adminService;
    }

    @GetMapping
    public String setEvaluation(@PathVariable String domain, Model model) {
        evaluationService.renderEvaluationSettingPage(domain, model);
        adminService.renderAdminSidebar(domain, model);
        return "admin/webpage_setting/set_evaluation";
    }

    @PostMapping("/create")
    public String evaluation(@PathVariable String domain, @ModelAttribute NewEvaluationForm newEvaluationForm) {
        evaluationService.saveNewEvaluation(domain, newEvaluationForm);
        return "redirect:" + domainPrefix + "admin/" + domain + "/setting/evaluation";
    }

    // TODO: "reserve" move to endpoint without /admin/
    @PostMapping("/reserve")
    public String reserveEvaluation(@PathVariable String domain, @ModelAttribute ReserveEvaluationForm reserveEvaluationForm) {
        evaluationService.reserveEvaluation(reserveEvaluationForm);
        return "redirect:" + domainPrefix + domain + "/evaluation.html";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteEvaluation(
            @PathVariable String id, @PathVariable String domain) {
        long idValue = Long.parseLong(id);
        evaluationService.deleteEvaluation(idValue);
        return ResponseEntity.ok("Delete evaluation id: " + id);
    }
}

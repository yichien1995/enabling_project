package tw.appworks.school.yichien.enabling.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.NewEvaluationForm;
import tw.appworks.school.yichien.enabling.dto.form.ReserveEvaluationForm;
import tw.appworks.school.yichien.enabling.service.EvaluationService;

@Controller
@RequestMapping("/admin/{domain}/setting/evaluation")
public class EvaluationController {

	private final EvaluationService evaluationService;

	@Value("${prefix.domain}")
	private String domainPrefix;

	public EvaluationController(EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}

	@GetMapping
	public String setArticle(@PathVariable String domain, Model model) {
		evaluationService.renderEvaluationSettingPage(domain, model);
		return "admin/set_evaluation";
	}

	@PostMapping("/create")
	public String evaluation(@PathVariable String domain, @ModelAttribute NewEvaluationForm newEvaluationForm) {
		evaluationService.saveNewEvaluation(domain, newEvaluationForm);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/evaluation";
	}

	@PostMapping("/reserve")
	public String reserveEvaluation(@PathVariable String domain, @ModelAttribute ReserveEvaluationForm reserveEvaluationForm) {
		evaluationService.reserveEvaluation(reserveEvaluationForm);
		return "redirect:" + domainPrefix + domain + "/evaluation.html";
	}

	@DeleteMapping("/delete")
	@ResponseBody
	public ResponseEntity<?> deleteEvaluation(
			@RequestParam String id, @PathVariable String domain) {
		long idValue = Long.parseLong(id);
		evaluationService.deleteEvaluation(idValue);
		return ResponseEntity.ok("Delete article id: " + id);
	}
}
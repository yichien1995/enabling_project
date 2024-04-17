package tw.appworks.school.yichien.enabling.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.service.AdminService;
import tw.appworks.school.yichien.enabling.service.HomepageService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/{domain}")
public class HomepageController {

	private final HomepageService homepageService;

	private final AdminService adminService;

	@Value("${prefix.domain}")
	private String domainPrefix;

	public HomepageController(HomepageService homepageService, AdminService adminService) {
		this.homepageService = homepageService;
		this.adminService = adminService;
	}

	@GetMapping("/setting/homepage")
	public String setHomepage(@PathVariable String domain, @RequestParam(required = false) String action, Model model) {

		if (action != null && action.equals("preview")) {
			homepageService.renderHomepagePreview(domain, model);
		} else {
			homepageService.renderHomepage(domain, model);
		}

		// institution info setting
		if (action != null && action.equals("update")) {
			model.addAttribute("action", "get-info-form");
		} else {
			model.addAttribute("action", "get-info");
		}
		homepageService.getInstitution(domain, model);

		return "admin/setHomepage";
	}

	@PostMapping("/setting/homepage/update/institution")
	public String updateInstitution(@PathVariable String domain, @ModelAttribute Institution institution) {
		homepageService.updateInstitution(domain, institution);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage";
	}

	@PostMapping("/setting/homepage/preview")
	public String previewHomepage(@PathVariable String domain, @ModelAttribute HomepageForm homepageForm) {
		//save preview data
		homepageService.saveHomepageDraft(domain, homepageForm);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage?action=preview";
	}

	@PostMapping("/setting/homepage/update/style")
	public String updateHomepage(@PathVariable String domain, @ModelAttribute HomepageForm homepageForm) {
		homepageService.saveHomepage(domain, homepageForm);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage";
	}

	@GetMapping("")
	public ResponseEntity<?> domainTest(@PathVariable String domain) {
		Map<String, Object> msg = new HashMap<>();
		boolean checkDomainExists = adminService.checkDomain(domain);
		if (!checkDomainExists) {
			msg.put("error", "domain not found");
		} else {
			msg.put("error", "you are going to the wrong page !");
		}
		return ResponseEntity.ok().body(msg);
	}
}
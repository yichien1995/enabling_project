package tw.appworks.school.yichien.enabling.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.service.AdminService;
import tw.appworks.school.yichien.enabling.service.HomepageService;

@Controller
@RequestMapping("/admin/{domain}/setting/homepage")
public class HomepageController {

	private final HomepageService homepageService;

	private final AdminService adminService;

	@Value("${prefix.domain}")
	private String domainPrefix;

	public HomepageController(HomepageService homepageService, AdminService adminService) {
		this.homepageService = homepageService;
		this.adminService = adminService;
	}

	@GetMapping("")
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

		return "admin/set_homepage";
	}

	@PostMapping("/update/institution")
	public String updateInstitution(@PathVariable String domain, @ModelAttribute Institution institution) {
		homepageService.updateInstitution(domain, institution);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage";
	}

	@PostMapping("/preview")
	public String previewHomepage(@PathVariable String domain, @ModelAttribute HomepageForm homepageForm) {
		System.out.println(homepageForm);
		//save preview data
		homepageService.saveHomepageDraft(domain, homepageForm);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage?action=preview";
	}

	@PostMapping("/update/style")
	public String updateHomepage(@PathVariable String domain, @ModelAttribute HomepageForm homepageForm) {
		homepageService.saveHomepage(domain, homepageForm);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage";
	}

//	@GetMapping("")
//	public ResponseEntity<?> domainTest(@PathVariable String domain) {
//		Map<String, Object> msg = new HashMap<>();
//		boolean checkDomainExists = adminService.checkDomain(domain);
//		if (!checkDomainExists) {
//			msg.put("error", "domain not found");
//		} else {
//			msg.put("error", "you are going to the wrong page !");
//		}
//		return ResponseEntity.ok().body(msg);
//	}
}

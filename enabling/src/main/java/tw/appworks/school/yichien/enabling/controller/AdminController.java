package tw.appworks.school.yichien.enabling.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.account.HomepagePreviewDTO;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.service.AdminService;
import tw.appworks.school.yichien.enabling.service.WebpageService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/{domain}")
public class AdminController {

	private final WebpageService webpageService;

	private final AdminService adminService;

	@Value("${prefix.domain}")
	private String domainPrefix;

	public AdminController(WebpageService webpageService, AdminService adminService) {
		this.webpageService = webpageService;
		this.adminService = adminService;
	}

	@GetMapping("/setting/homepage")
	public String setHomepage(@PathVariable String domain, @RequestParam(required = false) String action, Model model) {

		if (action != null && action.equals("preview")) {
			webpageService.renderHomepagePreview(domain, model);

			//			//check whether image is updated
////			System.out.println(previewPageData.getLogo().isEmpty());
////
////			if (previewPageData.getLogo().isEmpty()) {
////				model.addAttribute("image-update", "old");
////			}
////
////			if (previewPageData.getMainImage().isEmpty()) {
////				model.addAttribute("logo-update", false);
////			}
		} else {
			webpageService.renderHomepage(domain, model);
		}

		// institution info setting
		if (action != null && action.equals("update")) {
			model.addAttribute("action", "get-info-form");
		} else {
			model.addAttribute("action", "get-info");
		}
		webpageService.getInstitution(domain, model);

		return "admin/setHomepage";
	}

	@PostMapping("/setting/homepage/update/institution")
	public String updateInstitution(@PathVariable String domain, @ModelAttribute Institution institution) {
		webpageService.updateInstitution(domain, institution);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage";
	}

	@PostMapping("/setting/homepage/preview")
	public String previewHomepage(@PathVariable String domain, @ModelAttribute HomepageForm homepageForm) {
		//save preview data
		webpageService.saveHomepageDraft(domain, homepageForm);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage?action=preview";
	}

	@PostMapping("/setting/homepage/update/style")
	public String updateHomepage(@PathVariable String domain, @ModelAttribute HomepageForm homepageForm) {
		webpageService.saveHomepage(domain, homepageForm);
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

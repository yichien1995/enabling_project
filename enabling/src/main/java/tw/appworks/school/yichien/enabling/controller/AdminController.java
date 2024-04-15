package tw.appworks.school.yichien.enabling.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.account.HomepagePreviewDTO;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.service.AdminService;
import tw.appworks.school.yichien.enabling.service.WebpageService;

@Controller
@RequestMapping("/admin/{domain}")
public class AdminController {

	private final WebpageService webpageService;

	private final AdminService adminService;

	private HomepagePreviewDTO previewPageData;

	private HomepageForm previewFormData;

	@Value("${prefix.domain}")
	private String domainPrefix;

	public AdminController(WebpageService webpageService, AdminService adminService) {
		this.webpageService = webpageService;
		this.adminService = adminService;
	}

	@GetMapping("/setting/homepage")
	public String setHomepage(@PathVariable String domain, @RequestParam(required = false) String action, Model model) {

		// institution info setting
		if (action != null && action.equals("update")) {
			model.addAttribute("action", "get-info-form");
		} else {
			model.addAttribute("action", "get-info");
		}
		webpageService.getInstitution(domain, model);

		// homepage setting
		if (action != null && action.equals("preview")){
			model.addAttribute("preview","update-page");
			model.addAttribute("previewForm",previewFormData);
			model.addAttribute("previewPage",previewPageData);
		} else {
			model.addAttribute("preview","current-page");
		}

		webpageService.getHomepage(domain,model);
		webpageService.renderHomepage(domain, model);

		return "admin/setHomepage";
	}

	@PostMapping("/setting/homepage/update/institution")
	public String updateInstitution(@PathVariable String domain, @ModelAttribute Institution institution) {
		webpageService.updateInstitution(domain,institution);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage";
	}

	@PostMapping("/setting/homepage/preview")
	public String previewHomepage(@PathVariable String domain, @ModelAttribute HomepageForm homepageForm) {
		previewFormData = homepageForm;
		previewPageData = adminService.setPreviewData(homepageForm);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage?action=preview";
	}

	@PostMapping("/setting/homepage/update/style")
	public String updateHomepage(@PathVariable String domain, @ModelAttribute HomepageForm homepageForm) {
		webpageService.updateHomepage(domain,homepageForm);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage";
	}

//	@GetMapping("/")
//	public void domainTest(){
//		System.out.println(adminService.checkDomain());
//	}
}

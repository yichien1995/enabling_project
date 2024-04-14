package tw.appworks.school.yichien.enabling.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.service.WebpageService;

import java.util.Map;

@Controller
@RequestMapping("/admin/{domain}")
public class AdminController {

	private final WebpageService webpageService;

	@Value("${prefix.domain}")
	private String domainPrefix;

	public AdminController(WebpageService webpageService) {
		this.webpageService = webpageService;
	}

	@GetMapping("/setting/homepage")
	public String setHomepage(@PathVariable String domain, @RequestParam(required = false) String action, Model model) {
		if (action != null && action.equals("update")) {
			model.addAttribute("action", "get-form");
		} else {
			model.addAttribute("action", "get-info");
		}
		webpageService.renderHomepage(domain, model);
		webpageService.getInstitution(domain, model);
		return "admin/setHomepage";
	}

	@PostMapping("/setting/homepage/update")
	public String updateHomepage(@PathVariable String domain, @ModelAttribute Institution institution) {
		webpageService.updateInstitution(domain,institution);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage";
	}

	@PostMapping("/setting/homepage/update/color")
	public String updateStyle(@PathVariable String domain, @RequestParam String color) {
		int colorId = Integer.parseInt(color);
		webpageService.updateStyle(domain,colorId);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/homepage";
	}
}

package tw.appworks.school.yichien.enabling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.appworks.school.yichien.enabling.service.WebpageService;

@Controller
@RequestMapping("/admin/{domain}")
public class AdminController {

	private final WebpageService webpageService;

	public AdminController(WebpageService webpageService) {
		this.webpageService = webpageService;
	}

	@GetMapping("/setting/homepage")
	public String setHomepage(@PathVariable String domain, Model model) {
		webpageService.renderHomepage(domain, model);
		return "admin/setHomepage";
	}
}

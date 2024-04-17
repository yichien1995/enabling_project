package tw.appworks.school.yichien.enabling.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.appworks.school.yichien.enabling.service.HomepageService;

@Controller
@RequestMapping("/{domain}")
public class WebpageController {

	private final HomepageService homepageService;

	public WebpageController(HomepageService homepageService) {
		this.homepageService = homepageService;
	}

	@GetMapping("/homepage.html")
	public String HomePage(@PathVariable String domain, Model model) {
		homepageService.renderHomepage(domain, model);
		return "webpage/homepage";
	}

	// for testing
	@GetMapping
	public ResponseEntity<?> test() {
		return ResponseEntity.ok().body(homepageService.getHomepageDetail("test1"));
	}

}

package tw.appworks.school.yichien.enabling.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.appworks.school.yichien.enabling.service.WebpageService;

@Controller
@RequestMapping("/{domain}")
public class WebpageController {

	private final WebpageService webpageService;

	public WebpageController(WebpageService webpageService) {
		this.webpageService = webpageService;
	}

	@GetMapping("/homepage.html")
	public String HomePage(@PathVariable String domain, Model model) {
		webpageService.renderHomepage(domain, model);
		return "webpage/homepage";
	}

	@GetMapping
	public ResponseEntity<?> test() {
		return ResponseEntity.ok().body(webpageService.getHomepage("test1"));
	}

}

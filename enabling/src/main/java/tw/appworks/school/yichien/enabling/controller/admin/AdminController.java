package tw.appworks.school.yichien.enabling.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/{domain}")
public class AdminController {

	@GetMapping
	public String adminMainPage(@PathVariable String domain, Model model) {
		model.addAttribute("domain", domain);
		return "main_page/admin";
	}
}

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

	@GetMapping("/setting/team")
	public String setTeam(@PathVariable String domain, Model model) {
		model.addAttribute("domain", domain);
		return "admin/set_team";
	}

	@GetMapping("/setting/service")
	public String setService(@PathVariable String domain, Model model) {
		model.addAttribute("domain", domain);
		return "admin/set_service";
	}

	@GetMapping("/management/team")
	public String teamManagement(@PathVariable String domain, Model model) {
		model.addAttribute("domain", domain);
		return "admin/team_management";
	}

	@GetMapping("/management/account")
	public String accountManagement(@PathVariable String domain, Model model) {
		model.addAttribute("domain", domain);
		return "admin/account_management";
	}
}

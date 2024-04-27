package tw.appworks.school.yichien.enabling.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.appworks.school.yichien.enabling.service.TeamManagementService;


@Controller
@RequestMapping("/admin/{domain}/management")
public class ManagementController {

	private final TeamManagementService teamManagementService;

	public ManagementController(TeamManagementService teamManagementService) {
		this.teamManagementService = teamManagementService;
	}

	@GetMapping("/team")
	public String teamManagement(@PathVariable String domain, Model model) {
		teamManagementService.renderTeamManagementPage(domain, model);
		model.addAttribute("domain", domain);
		return "admin/team_management";
	}

	@GetMapping("/account")
	public String accountManagement(@PathVariable String domain, Model model) {
		model.addAttribute("domain", domain);
		return "admin/account_management";
	}

	@GetMapping("/report")
	public String caseReport(@PathVariable String domain, Model model) {
		model.addAttribute("domain", domain);
		return "admin/report";
	}
}
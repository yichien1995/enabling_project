package tw.appworks.school.yichien.enabling.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tw.appworks.school.yichien.enabling.service.AdminService;
import tw.appworks.school.yichien.enabling.service.ReportService;
import tw.appworks.school.yichien.enabling.service.TeamManagementService;
import tw.appworks.school.yichien.enabling.service.webpage.HomepageService;


@Controller
@RequestMapping("/admin/{domain}/management")
public class ManagementController {

	private final TeamManagementService teamManagementService;

	private final ReportService reportService;

	private final HomepageService homepageService;

	private final AdminService adminService;

	public ManagementController(TeamManagementService teamManagementService, ReportService reportService, HomepageService homepageService, AdminService adminService) {
		this.teamManagementService = teamManagementService;
		this.reportService = reportService;
		this.homepageService = homepageService;
		this.adminService = adminService;
	}

	@GetMapping("/team")
	public String teamManagement(@PathVariable String domain, Model model) {
		teamManagementService.renderTeamManagementPage(domain, model);
		adminService.renderAdminSidebar(domain, model);
		return "admin/team_management";
	}

	@GetMapping("/account")
	public String accountManagement(@PathVariable String domain, @RequestParam(required = false) String action, Model model) {
		model.addAttribute("domain", domain);
		if (action != null && action.equals("update")) {
			model.addAttribute("action", "get-info-form");
		} else {
			model.addAttribute("action", "get-info");
		}
		homepageService.getInstitution(domain, model);
		adminService.renderAdminSidebar(domain, model);
		return "admin/account_management";
	}

	@GetMapping("/report")
	public String caseReport(@PathVariable String domain, Model model) {
		reportService.renderReportPage(domain, model);
		adminService.renderAdminSidebar(domain, model);
		return "admin/report";
	}
	
}

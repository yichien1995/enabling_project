package tw.appworks.school.yichien.enabling.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tw.appworks.school.yichien.enabling.service.AdminService;
import tw.appworks.school.yichien.enabling.service.webpage.HomepageService;
import tw.appworks.school.yichien.enabling.service.webpage.MemberService;
import tw.appworks.school.yichien.enabling.service.webpage.ServiceItemService;

@Controller
@RequestMapping("/admin/{domain}")
public class AdminController {

	private final HomepageService homepageService;

	private final ServiceItemService serviceItemService;

	private final AdminService adminService;

	private final MemberService memberService;

	public AdminController(HomepageService homepageService, ServiceItemService serviceItemService, AdminService adminService, MemberService memberService) {
		this.homepageService = homepageService;
		this.serviceItemService = serviceItemService;
		this.adminService = adminService;
		this.memberService = memberService;
	}

	@GetMapping
	public String adminMainPage(@PathVariable String domain, Model model) {
		adminService.renderAdminSidebar(domain, model);
		return "main_page/admin";
	}

	@GetMapping("/setting/homepage")
	public String setHomepage(@PathVariable String domain, @RequestParam(required = false) String action, Model model) {

		adminService.renderAdminSidebar(domain, model);

		if (action != null && action.equals("preview")) {
			homepageService.renderHomepagePreview(domain, model);
			return "webpage/homepage";
		}
		homepageService.renderHomePage(domain, model);
		return "admin/webpage_setting/set_homepage";
	}

	@GetMapping("/setting/member")
	public String setTeam(@PathVariable String domain, Model model) {
		adminService.renderAdminSidebar(domain, model);
		memberService.renderMemberPage(domain, model);
		return "admin/webpage_setting/set_member";
	}

	@GetMapping("/setting/service")
	public String setService(@PathVariable String domain, Model model) {
		adminService.renderAdminSidebar(domain, model);
		serviceItemService.renderServicePage(domain, model);
		return "admin/webpage_setting/set_service";
	}
}

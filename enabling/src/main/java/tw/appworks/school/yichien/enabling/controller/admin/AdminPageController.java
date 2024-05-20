package tw.appworks.school.yichien.enabling.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tw.appworks.school.yichien.enabling.service.AdminService;
import tw.appworks.school.yichien.enabling.service.ClientManagementService;
import tw.appworks.school.yichien.enabling.service.ReportService;
import tw.appworks.school.yichien.enabling.service.TeamManagementService;
import tw.appworks.school.yichien.enabling.service.webpage.*;

@Controller
@RequestMapping("/admin/{domain}")
public class AdminPageController {

    private static final Logger logger = LoggerFactory.getLogger(AdminPageController.class);
    private final HomepageService homepageService;
    private final ServiceItemService serviceItemService;
    private final AdminService adminService;
    private final MemberService memberService;
    private final ArticleService articleService;
    private final EvaluationService evaluationService;
    private final TeamManagementService teamManagementService;
    private final ReportService reportService;
    private final ClientManagementService clientManagementService;


    public AdminPageController(HomepageService homepageService,
                               ServiceItemService serviceItemService,
                               AdminService adminService, MemberService memberService,
                               ArticleService articleService,
                               EvaluationService evaluationService,
                               TeamManagementService teamManagementService,
                               ReportService reportService,
                               ClientManagementService clientManagementService) {
        this.homepageService = homepageService;
        this.serviceItemService = serviceItemService;
        this.adminService = adminService;
        this.memberService = memberService;
        this.articleService = articleService;
        this.evaluationService = evaluationService;
        this.teamManagementService = teamManagementService;
        this.reportService = reportService;
        this.clientManagementService = clientManagementService;
    }

    @GetMapping
    public String adminMainPage(@PathVariable String domain, Model model) {
        adminService.renderAdminSidebar(domain, model);
        homepageService.renderHomePage(domain, model);
        return "admin/webpage_setting/set_homepage";
//		return "main_page/admin";
    }

    // webpage Management
    @GetMapping("/webpage/homepage")
    public String setHomepage(@PathVariable String domain,
                              @RequestParam(required = false) String action, Model model) {

        adminService.renderAdminSidebar(domain, model);

        if (action != null && action.equals("preview")) {
            homepageService.renderHomepagePreview(domain, model);
            return "webpage/homepage";
        }
        homepageService.renderHomePage(domain, model);
        return "admin/webpage_setting/set_homepage";
    }

    @GetMapping("/webpage/service")
    public String setService(@PathVariable String domain, Model model) {
        adminService.renderAdminSidebar(domain, model);
        serviceItemService.renderServicePage(domain, model);
        return "admin/webpage_setting/set_service";
    }

    @GetMapping("/webpage/member")
    public String setTeam(@PathVariable String domain, Model model) {
        adminService.renderAdminSidebar(domain, model);
        memberService.renderMemberPage(domain, model);
        return "admin/webpage_setting/set_member";
    }

    @GetMapping("/webpage/article")
    public String setArticle(@PathVariable String domain, Model model) {
        articleService.renderArticleList(domain, model);
        adminService.renderAdminSidebar(domain, model);
        return "admin/webpage_setting/set_article";
    }

    @GetMapping("/webpage/article/{id}")
    public String getArticle(@PathVariable String domain, @PathVariable String id,
                             @RequestParam(required = false) int draft, Model model) {
//        if (draft == 0) {
//            model.addAttribute("released", "released");
//        }
        articleService.renderPageByArticleId(id, model);
        articleService.renderArticleList(domain, model);
        adminService.renderAdminSidebar(domain, model);
        return "admin/webpage_setting/set_article";
    }

    @GetMapping("webpage/article/preview")
    public String previewArticle(@PathVariable String domain, Model model) {
        homepageService.renderHeaderAndFooter(domain, model);
        articleService.renderArticlePreviewPage(domain, model);
        return "admin/webpage_setting/preview_article";
    }


    @GetMapping("/webpage/evaluation")
    public String setEvaluation(@PathVariable String domain, Model model) {
        evaluationService.renderEvaluationSettingPage(domain, model);
        adminService.renderAdminSidebar(domain, model);
        return "admin/webpage_setting/set_evaluation";
    }

    // team Management
    @GetMapping("/team/member")
    public String teamManagement(@PathVariable String domain, Model model) {
        teamManagementService.renderTeamManagementPage(domain, model);
        adminService.renderAdminSidebar(domain, model);
        return "admin/team_management";
    }

    @GetMapping("/team/report")
    public String caseReport(@PathVariable String domain, Model model) {
        reportService.renderReportPage(domain, model);
        adminService.renderAdminSidebar(domain, model);
        return "admin/report";
    }

    // client Management
    @GetMapping("/client")
    public String clientManagement(@PathVariable String domain, Model model) {
        adminService.renderAdminSidebar(domain, model);
        clientManagementService.renderClientManagementPage(domain, model);
        return "admin/client_management";
    }

    // account Management
    @GetMapping("/account")
    public String accountManagement(@PathVariable String domain,
                                    @RequestParam(required = false) String action, Model model) {
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
}

package tw.appworks.school.yichien.enabling.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.appworks.school.yichien.enabling.service.AdminService;
import tw.appworks.school.yichien.enabling.service.google.GeocodingServiceImpl;
import tw.appworks.school.yichien.enabling.service.webpage.*;

@Controller
@RequestMapping("/{domain}")
public class WebpageController {

	private final HomepageService homepageService;

	private final ArticleService articleService;

	private final EvaluationService evaluationService;

	private final ServiceItemService serviceItemService;

	private final MemberService memberService;

	private final AdminService adminService;

	private final GeocodingServiceImpl geocodingService;

	@Value("${prefix.domain}")
	private String domainPrefix;

	@Value("${google.api.key}")
	private String API_KEY;

	public WebpageController(HomepageService homepageService, ArticleService articleService, EvaluationService evaluationService, ServiceItemService serviceItemService, MemberService memberService, AdminService adminService, GeocodingServiceImpl geocodingService) {
		this.homepageService = homepageService;
		this.articleService = articleService;
		this.evaluationService = evaluationService;
		this.serviceItemService = serviceItemService;
		this.memberService = memberService;
		this.adminService = adminService;
		this.geocodingService = geocodingService;
	}

	@GetMapping("/homepage.html")
	public String HomePage(@PathVariable String domain, Model model) {
		boolean checkDomainExists = adminService.checkDomain(domain);
		if (!checkDomainExists) {
			return "redirect:" + domainPrefix;
		}
		homepageService.renderHomePage(domain, model);
		model.addAttribute("apiKey", API_KEY);
		return "webpage/homepage";
	}

	@GetMapping("/articles.html")
	public String articleListPage(@PathVariable String domain, Model model) {
		boolean checkDomainExists = adminService.checkDomain(domain);
		if (!checkDomainExists) {
			return "redirect:" + domainPrefix;
		}
		homepageService.renderHeaderAndFooter(domain, model);
		articleService.renderArticleListPage(domain, model);
		return "webpage/articles";
	}

	@GetMapping("/article/{id}")
	public String articlePage(@PathVariable String domain,
	                          @PathVariable String id, Model model) {
		boolean checkDomainExists = adminService.checkDomain(domain);
		if (!checkDomainExists) {
			return "redirect:" + domainPrefix;
		}
		homepageService.renderHeaderAndFooter(domain, model);
		articleService.renderPageByArticleId(id, model);
		return "webpage/article_page";
	}

	@GetMapping("/evaluation.html")
	public String evaluationPage(@PathVariable String domain, Model model) {
		boolean checkDomainExists = adminService.checkDomain(domain);
		if (!checkDomainExists) {
			return "redirect:" + domainPrefix;
		}
		homepageService.renderHeaderAndFooter(domain, model);
		evaluationService.renderEvaluationPage(domain, model);
		return "webpage/evaluation";
	}

	@GetMapping("/service.html")
	public String servicePage(@PathVariable String domain, Model model) {
		boolean checkDomainExists = adminService.checkDomain(domain);
		if (!checkDomainExists) {
			return "redirect:" + domainPrefix;
		}
		homepageService.renderHeaderAndFooter(domain, model);
		serviceItemService.renderServicePage(domain, model);
		return "webpage/service";
	}

	@GetMapping("/member.html")
	public String teamPage(@PathVariable String domain, Model model) {
		boolean checkDomainExists = adminService.checkDomain(domain);
		if (!checkDomainExists) {
			return "redirect:" + domainPrefix;
		}
		homepageService.renderHeaderAndFooter(domain, model);
		memberService.renderMemberPage(domain, model);
		return "webpage/member";
	}
}

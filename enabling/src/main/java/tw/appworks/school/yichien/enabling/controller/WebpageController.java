package tw.appworks.school.yichien.enabling.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.appworks.school.yichien.enabling.service.ArticleService;
import tw.appworks.school.yichien.enabling.service.EvaluationService;
import tw.appworks.school.yichien.enabling.service.HomepageService;

@Controller
@RequestMapping("/{domain}")
public class WebpageController {

	private final HomepageService homepageService;

	private final ArticleService articleService;

	private final EvaluationService evaluationService;

	public WebpageController(HomepageService homepageService, ArticleService articleService, EvaluationService evaluationService) {
		this.homepageService = homepageService;
		this.articleService = articleService;
		this.evaluationService = evaluationService;
	}

	@GetMapping("/homepage.html")
	public String HomePage(@PathVariable String domain, Model model) {
		homepageService.renderHomepage(domain, model);
		return "webpage/homepage";
	}

	@GetMapping("/articles.html")
	public String articleListPage(@PathVariable String domain, Model model) {
		homepageService.renderHomepage(domain, model);
		articleService.renderArticleListPage(domain, model);
		return "webpage/articles";
	}

	@GetMapping("/article/{id}")
	public String articlePage(@PathVariable String domain,
	                          @PathVariable String id, Model model) {
		homepageService.renderHomepage(domain, model);
		articleService.renderPageByArticleId(id, model);
		return "webpage/article_page";
	}

	@GetMapping("/evaluation.html")
	public String evaluationPage(@PathVariable String domain, Model model) {
		homepageService.renderHomepage(domain, model);
		evaluationService.renderEvaluationPage(domain, model);
		return "webpage/evaluation";
	}

	// for testing
	@GetMapping
	public ResponseEntity<?> test() {
		return ResponseEntity.ok().body(homepageService.getHomepageDetail("test1"));
	}

}

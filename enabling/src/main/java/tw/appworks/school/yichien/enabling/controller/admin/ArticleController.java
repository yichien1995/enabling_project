package tw.appworks.school.yichien.enabling.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.ArticleForm;
import tw.appworks.school.yichien.enabling.service.ArticleService;


@Controller
@RequestMapping("/admin/{domain}/setting/articles")
public class ArticleController {

	@Value("${prefix.domain}")
	private String domainPrefix;

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping
	public String setArticle(@PathVariable String domain, Model model) {
		articleService.renderArticleList(domain, model);
		return "admin/set_article";
	}

	@PostMapping("/save")
	public String saveArticle(@PathVariable String domain,
	                          @RequestParam(required = false, defaultValue = "1") String draft,
	                          @RequestParam String action
			, @ModelAttribute ArticleForm articleForm) {
		System.out.println(action);
		int draftValue = Integer.parseInt(draft);
		articleService.saveArticle(domain, draftValue, articleForm);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/articles";
	}

	@GetMapping("/{title}")
	public String getArticle(@PathVariable String domain, @PathVariable String title,
	                         @RequestParam(required = false) int draft, Model model) {
		if (draft == 0) {
			model.addAttribute("released", "released");
		}
		articleService.getArticle(domain, title, draft, model);
		articleService.renderArticleList(domain, model);
		return "admin/set_article";
	}

	@PostMapping("/release")
	public String releaseArticle(@PathVariable String domain, @ModelAttribute ArticleForm articleForm) {
		articleService.saveArticle(domain, 0, articleForm);
		articleService.deleteArticle(domain, articleForm.getTitle(), 1);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/articles";
	}

	@PostMapping("/delete")
	public String deleteArticle(@PathVariable String domain, @ModelAttribute ArticleForm articleForm) {
		articleService.deleteArticle(domain, articleForm.getTitle(), 0);
		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/articles";
	}
}

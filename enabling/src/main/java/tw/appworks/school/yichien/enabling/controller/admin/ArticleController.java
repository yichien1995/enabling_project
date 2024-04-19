package tw.appworks.school.yichien.enabling.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.ArticleForm;
import tw.appworks.school.yichien.enabling.service.ArticleService;


@Controller
@RequestMapping("/admin/{domain}/setting/articles")
public class ArticleController {

	private final ArticleService articleService;
	@Value("${prefix.domain}")
	private String domainPrefix;

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
	                          @RequestParam String action,
	                          @RequestParam(required = false) String id
			, @ModelAttribute ArticleForm articleForm) {
		int previewStatus = action.equals("preview") ? 1 : 0;
		int draftValue = action.equals("release") ? 0 : Integer.parseInt(draft);

		if (action.equals("preview")) {
			articleService.savePreviewArticlePage(domain, draftValue, previewStatus, articleForm);
			return "redirect:" + domainPrefix + "admin/" + domain + "/setting/articles";
		}

		if (id != null) {
			int idValue = Integer.parseInt(id);
			articleService.updateArticle(idValue, draftValue, previewStatus, articleForm);
		} else {
			articleService.saveNewArticle(domain, draftValue, previewStatus, articleForm);
		}

		return "redirect:" + domainPrefix + "admin/" + domain + "/setting/articles";
	}

	@GetMapping("/{id}")
	public String getArticle(@PathVariable String domain, @PathVariable String id,
	                         @RequestParam(required = false) int draft, Model model) {
		if (draft == 0) {
			model.addAttribute("released", "released");
		}
		articleService.renderPageByArticleId(id, model);
		articleService.renderArticleList(domain, model);
		return "admin/set_article";
	}

	@DeleteMapping("/delete")
	@ResponseBody
	public ResponseEntity<?> deleteArticle(
			@RequestParam(required = true) String id, @PathVariable String domain) {
		int idValue = Integer.parseInt(id);
		articleService.deleteArticle(idValue);
		return ResponseEntity.ok("Delete article id: " + id);
	}
}

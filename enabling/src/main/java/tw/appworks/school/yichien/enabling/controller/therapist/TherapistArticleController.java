package tw.appworks.school.yichien.enabling.controller.therapist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.ArticleForm;
import tw.appworks.school.yichien.enabling.service.AdminService;
import tw.appworks.school.yichien.enabling.service.webpage.ArticleService;
import tw.appworks.school.yichien.enabling.service.webpage.HomepageService;

@Controller
@RequestMapping("/therapist/{domain}/setting/articles")
public class TherapistArticleController {

	private final ArticleService articleService;

	private final AdminService adminService;

	private final HomepageService homepageService;

	@Value("${prefix.domain}")
	private String domainPrefix;

	public TherapistArticleController(ArticleService articleService, AdminService adminService, HomepageService homepageService) {
		this.articleService = articleService;
		this.adminService = adminService;
		this.homepageService = homepageService;
	}


	@GetMapping
	public String setArticle(@PathVariable String domain, Model model) {
		articleService.renderArticleList(domain, model);
//		adminService.renderAdminSidebar(domain, model);
		return "therapist/set_article_therapist";
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
			if (id == null || id.isEmpty()) {
				articleService.savePreviewArticlePage(domain, draftValue, previewStatus, articleForm);
			} else {
				int idValue = Integer.parseInt(id);
				articleService.previewExistArticle(idValue, domain, draftValue, previewStatus, articleForm);
			}
			return "redirect:" + domainPrefix + "therapist/" + domain + "/setting/articles";
		}

		if (id != null) {
			int idValue = Integer.parseInt(id);
			articleService.updateArticle(idValue, draftValue, previewStatus, articleForm);
		} else {
			articleService.saveNewArticle(domain, draftValue, previewStatus, articleForm);
		}

		return "redirect:" + domainPrefix + "therapist/" + domain + "/setting/articles";
	}

	@GetMapping("/{id}")
	public String getArticle(@PathVariable String domain, @PathVariable String id,
	                         @RequestParam(required = false) int draft, Model model) {
		if (draft == 0) {
			model.addAttribute("released", "released");
		}
		articleService.renderPageByArticleId(id, model);
		articleService.renderArticleList(domain, model);
		adminService.renderAdminSidebar(domain, model);
		return "therapist/set_article_therapist";
	}

	@GetMapping("/preview")
	public String previewArticle(@PathVariable String domain, Model model) {
		homepageService.renderHomepage(domain, model);
		articleService.renderArticlePreviewPage(domain, model);
		return "admin/webpage_setting/preview_article";
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

package tw.appworks.school.yichien.enabling.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.ArticleForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Article;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.repository.webpage.ArticleRepository;
import tw.appworks.school.yichien.enabling.service.ArticleService;
import tw.appworks.school.yichien.enabling.service.FileStorageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

	private final InstitutionRepository institutionRepository;
	private final ArticleRepository articleRepository;

	private final FileStorageService fileStorageService;

	public ArticleServiceImpl(InstitutionRepository institutionRepository, ArticleRepository articleRepository, FileStorageService fileStorageService) {
		this.institutionRepository = institutionRepository;
		this.articleRepository = articleRepository;
		this.fileStorageService = fileStorageService;
	}

	@Override
	public void saveArticle(String domain, int draft, ArticleForm articleForm) {
		Institution institution = institutionRepository.findByDomainName(domain);
		Article existingArticle =
				articleRepository.findByInstitutionDomainAndTitleAndDraft(institution, articleForm.getTitle(), draft);

		if (existingArticle != null) {
			existingArticle.setContent(articleForm.getContent());
			if (!articleForm.getCover().isEmpty()) {
				String uploadAndGetPath = fileStorageService.uploadFile(domain, articleForm.getTitle(), articleForm.getCover());
				existingArticle.setCover(uploadAndGetPath);
			}
			articleRepository.save(existingArticle);

		} else {
			Article article = new Article();

			article.setInstitutionDomain(institution);
			article.setDraft(draft);
			article.setTitle(articleForm.getTitle());

			String uploadAndGetPath = fileStorageService.uploadFile(domain, articleForm.getTitle(), articleForm.getCover());
			article.setCover(uploadAndGetPath);
			article.setContent(articleForm.getContent());
			articleRepository.save(article);
		}
	}

	@Override
	public void getArticle(String domain, String title, int draft, Model model) {
		Institution institution = institutionRepository.findByDomainName(domain);
		model.addAttribute("article", articleRepository.findByInstitutionDomainAndTitleAndDraft(institution, title, draft));

	}

	@Override
	@Transactional
	public void deleteArticle(String domain, String title, int draft) {
		Institution institution = institutionRepository.findByDomainName(domain);
		articleRepository.deleteArticleByInstitutionDomainAndTitleAndDraft(institution, title, draft);
	}

	public void renderArticleList(String domain, Model model) {
		List<String> released = getArticleList(domain, 0);
		model.addAttribute("released_article", released);
		List<String> drafts = getArticleList(domain, 1);
		model.addAttribute("drafts", drafts);
		model.addAttribute("domain", domain);
	}

	;

	private List<String> getArticleList(String domain, int draft) {
		Institution institution = institutionRepository.findByDomainName(domain);
		List<Article> articles = articleRepository.findTitlesByInstitutionDomainAndDraft(institution, draft);
		return articles.stream()
				.map(Article::getTitle)
				.collect(Collectors.toList());
	}
}

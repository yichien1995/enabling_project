package tw.appworks.school.yichien.enabling.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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

@Service
public class ArticleServiceImpl implements ArticleService {

	public static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	private final InstitutionRepository institutionRepository;
	private final ArticleRepository articleRepository;
	private final FileStorageService fileStorageService;

	@Value("${prefix.image}")
	private String imageUrlPrefix;

	@Autowired
	private EntityManager entityManager;

	public ArticleServiceImpl(InstitutionRepository institutionRepository, ArticleRepository articleRepository, FileStorageService fileStorageService) {
		this.institutionRepository = institutionRepository;
		this.articleRepository = articleRepository;
		this.fileStorageService = fileStorageService;
	}

	@Override
	public void updateArticle(int articleId, int draft, int preview, ArticleForm articleForm) {
		Article existArticle = articleRepository.findArticleById(articleId);

		if (!articleForm.getCover().isEmpty()) {
			String uploadAndGetPath = fileStorageService.uploadFile(existArticle.getInstitutionDomain().getDomainName(), articleForm.getTitle(), articleForm.getCover());
			existArticle.setCover(uploadAndGetPath);
		}
		existArticle.setTitle(articleForm.getTitle());
		existArticle.setContent(articleForm.getContent());
		existArticle.setDraft(draft);
		existArticle.setPreview(preview);
		articleRepository.save(existArticle);
	}


	@Override
	public void saveNewArticle(String domain, int draft, int preview, ArticleForm articleForm) {
		Article article = new Article();
		Institution institution = institutionRepository.getInstitution(domain);

		article.setTitle(articleForm.getTitle());
		article.setContent(articleForm.getContent());

		article.setDraft(draft);
		article.setPreview(preview);

		// save image relative URL
		String uploadAndGetPath = fileStorageService.uploadFile(institution.getDomainName(), articleForm.getTitle(), articleForm.getCover());
		article.setCover(uploadAndGetPath);

		article.setInstitutionDomain(institution);

		articleRepository.save(article);
	}

	@Override
	public void savePreviewArticlePage(String domain, int draft, int preview, ArticleForm articleForm) {
		try {
			Article existPreviewArticle = articleRepository.getPreviewArticle(domain);
			if (existPreviewArticle == null) {
				saveNewArticle(domain, draft, preview, articleForm);
			} else {
				existPreviewArticle.setTitle(articleForm.getTitle());
				existPreviewArticle.setContent(articleForm.getContent());
				// save image relative URL
				String uploadAndGetPath = fileStorageService.uploadFile(domain, "preview", articleForm.getCover());
				existPreviewArticle.setCover(uploadAndGetPath);
				articleRepository.save(existPreviewArticle);
			}
		} catch (IncorrectResultSizeDataAccessException e) {
			logger.error("Error in savePreviewArticlePage: {}", e.getMessage());
		}
	}

	@Override
	public void renderPageByArticleId(String id, Model model) {
		int idValue = Integer.parseInt(id);
		Article article = articleRepository.findArticleById(idValue);
		article.setCover(imageUrlPrefix + article.getCover());
		model.addAttribute("article", article);
	}

	@Override
	public void renderArticlePreviewPage(String domain, Model model) {
		Article previewArticle = articleRepository.getPreviewArticle(domain);
		previewArticle.setCover(imageUrlPrefix + previewArticle.getCover());
		model.addAttribute("article", previewArticle);
	}

	@Override
	@Transactional
	public void deleteArticle(int id) {
		articleRepository.deleteArticleById(id);
	}

	public void renderArticleList(String domain, Model model) {
		List<Article> released = getArticleList(domain, 0);
		model.addAttribute("released_article", released);
		List<Article> drafts = getArticleList(domain, 1);
		model.addAttribute("drafts", drafts);
		model.addAttribute("domain", domain);
	}

	;

	@Override
	public void renderArticleListPage(String domain, Model model) {
		List<Article> articles = getReleasedArticlesList(domain);
		for (Article article : articles) {
			article.setCover(imageUrlPrefix + article.getCover());
		}
		model.addAttribute("articles", articles);
	}

	private List<Article> getArticleList(String domain, int draft) {
		Institution institution = institutionRepository.findByDomainName(domain);
		return articleRepository.findArticleByInstitutionDomainAndDraftAndPreview(institution, draft, 0);
	}

	private List<Article> getReleasedArticlesList(String domain) {
		return articleRepository.getReleasedArticles(domain);
	}
}

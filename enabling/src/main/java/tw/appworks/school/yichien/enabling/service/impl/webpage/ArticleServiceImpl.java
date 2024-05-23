package tw.appworks.school.yichien.enabling.service.impl.webpage;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.ArticleForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Article;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.repository.webpage.ArticleRepository;
import tw.appworks.school.yichien.enabling.service.FileStorageService;
import tw.appworks.school.yichien.enabling.service.impl.S3UploadServiceImpl;
import tw.appworks.school.yichien.enabling.service.webpage.ArticleService;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    public static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    private final InstitutionRepository institutionRepository;
    private final ArticleRepository articleRepository;
    private final FileStorageService fileStorageService;
    private final S3UploadServiceImpl s3UploadService;

    @Value("${prefix.image}")
    private String imageUrlPrefix;

    public ArticleServiceImpl(InstitutionRepository institutionRepository,
                              ArticleRepository articleRepository,
                              FileStorageService fileStorageService,
                              S3UploadServiceImpl s3UploadService) {
        this.institutionRepository = institutionRepository;
        this.articleRepository = articleRepository;
        this.fileStorageService = fileStorageService;
        this.s3UploadService = s3UploadService;
    }

    @Override
    public void updateArticle(int articleId, int draft, int preview, ArticleForm form) {
        Article existArticle = articleRepository.findArticleById(articleId);

        if (!form.getCover().isEmpty()) {
            String uploadAndGetPath = fileStorageService.uploadFile(existArticle.getInstitutionDomain().getDomainName(), form.getTitle(), form.getCover());
//            String uploadAndGetPath = s3UploadService.uploadFileToS3(existArticle.getInstitutionDomain().getDomainName(),
//                    form.getCover().getOriginalFilename(), form.getCover());
            existArticle.setCover(uploadAndGetPath);
        }
        articleRepository.save(Article.convertUpdateForm(form, existArticle, draft, preview));
    }

    @Override
    public void saveNewArticle(String domain, int draft, int preview, ArticleForm form) {
        Institution institution = institutionRepository.getInstitution(domain);

        // save image relative URL
        String uploadAndGetPath = fileStorageService.uploadFile(institution.getDomainName(), form.getTitle(), form.getCover());
//        String uploadAndGetPath = s3UploadService.uploadFileToS3(institution.getDomainName(),
//                form.getCover().getOriginalFilename(), form.getCover());

        articleRepository.save(Article.convertNewForm(form, draft, preview, uploadAndGetPath, institution));
    }

    @Override
    public void savePreviewArticlePage(String domain, int draft, int preview, ArticleForm form) {
        handlePreviewArticle(domain, draft, preview, form, null);
    }

    @Override
    public void previewExistArticle(int articleId, String domain, int draft, int preview, ArticleForm form) {
        Article existArticle = articleRepository.findArticleById(articleId);
        handlePreviewArticle(domain, draft, preview, form, existArticle);
    }

    private void handlePreviewArticle(String domain, int draft, int preview, ArticleForm form, Article existArticle) {
        try {
            Article existPreviewArticle = articleRepository.getPreviewArticle(domain);
            if (existPreviewArticle == null) {
                saveNewArticle(domain, draft, preview, form);
            } else {
                if (existArticle != null && form.getCover().isEmpty()) {
                    existPreviewArticle.setCover(existArticle.getCover());
                } else {
                    String uploadAndGetPath = fileStorageService.uploadFile(domain, "preview", form.getCover());
                    existPreviewArticle.setCover(uploadAndGetPath);
                }
                articleRepository.save(Article.convertUpdateForm(form, existPreviewArticle, draft, preview));
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("Error in handlePreviewArticle: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteArticle(int id) {
        articleRepository.deleteArticleById(id);
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

    public void renderArticleList(String domain, Model model) {
        List<Article> released = getArticleList(domain, 0);
        model.addAttribute("released_article", released);
        List<Article> drafts = getArticleList(domain, 1);
        model.addAttribute("drafts", drafts);
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

    @Override
    public boolean checkArticleId(String domain, int id) {
        List<Integer> articleIdList = getReleasedArticlesList(domain).stream().map(Article::getId).toList();
        return articleIdList.contains(id);
    }
}

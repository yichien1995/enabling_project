package tw.appworks.school.yichien.enabling.service.webpage;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.ArticleForm;

public interface ArticleService {
    void deleteArticle(int id);

    void renderPageByArticleId(String id, Model model);

    void renderArticleList(String domain, Model model);

    void renderArticleListPage(String domain, Model model);

    void renderArticlePreviewPage(String domain, Model model);

    void saveNewArticle(String domain, int draft, int preview, ArticleForm articleForm);

    void updateArticle(int articleId, int draft, int preview, ArticleForm articleForm);

    void savePreviewArticlePage(String domain, int draft, int preview, ArticleForm articleForm);

    void previewExistArticle(int articleId, String domain, int draft, int preview, ArticleForm articleForm);

    boolean checkArticleId(String domain, int id);

}
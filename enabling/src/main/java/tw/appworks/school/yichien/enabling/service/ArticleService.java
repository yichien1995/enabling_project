package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.ArticleForm;

public interface ArticleService {

	void renderPageByArticleId(String id, Model model);

	void deleteArticle(int id);

	void renderArticleList(String domain, Model model);

	void saveNewArticle(String domain, int draft, int preview, ArticleForm articleForm);

	void updateArticle(int articleId, int draft, int preview, ArticleForm articleForm);

	void savePreviewArticlePage(String domain, int draft, int preview, ArticleForm articleForm);
}
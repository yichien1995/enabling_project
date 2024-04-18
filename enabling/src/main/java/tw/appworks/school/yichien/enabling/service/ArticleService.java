package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.ArticleForm;

public interface ArticleService {

	void saveArticle(String domain, int draft, ArticleForm articleForm);

	void getArticle(String domain, String title, int draft, Model model);

	void deleteArticle(String domain, String title, int draft);

	void renderArticleList(String domain, Model model);
}
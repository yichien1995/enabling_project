package tw.appworks.school.yichien.enabling.repository.webpage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
	Article findArticleById(int id);

	void deleteArticleById(int id);

	List<Article> findArticleByInstitutionDomainAndDraftAndPreview(Institution institutionDomain, int draft, int preview);

	@Query(value = "SELECT * FROM article WHERE institution_domain = :domain AND preview = 1", nativeQuery = true)
	Article getPreviewArticle(@Param("domain") String domain);

	@Query(value = "SELECT * FROM article WHERE institution_domain = :domain AND draft = 0", nativeQuery = true)
	List<Article> getReleasedArticles(@Param("domain") String domain);
}


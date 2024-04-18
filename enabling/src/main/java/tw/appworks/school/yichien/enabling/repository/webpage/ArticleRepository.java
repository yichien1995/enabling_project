package tw.appworks.school.yichien.enabling.repository.webpage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	Article findByInstitutionDomainAndTitleAndDraft(Institution institution, String title, int draft);

	void deleteArticleByInstitutionDomainAndTitleAndDraft(Institution institution, String title, int draft);

	List<Article> findTitlesByInstitutionDomainAndDraft(Institution institutionDomain, int draft);
}


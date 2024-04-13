package tw.appworks.school.yichien.enabling.repository.webpage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.webpage.Homepage;

@Repository
public interface HomepageRepository extends JpaRepository<Homepage,Integer> {
	@Query(value = "SELECT * FROM homepage AS h WHERE h.institution_domain = :domainName", nativeQuery = true)
	Homepage getHomepage(@Param("domainName") String domain);
}

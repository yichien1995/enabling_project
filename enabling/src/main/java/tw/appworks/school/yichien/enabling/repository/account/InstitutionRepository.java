package tw.appworks.school.yichien.enabling.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.account.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

	@Query(value = "SELECT * FROM institution WHERE domain_name = :domainName", nativeQuery = true)
	Institution getInstitution(@Param("domainName") String domain);

	Institution findByDomainName(String domain);

	// for testing
	boolean existsInstitutionByDomainName(String domain);

}

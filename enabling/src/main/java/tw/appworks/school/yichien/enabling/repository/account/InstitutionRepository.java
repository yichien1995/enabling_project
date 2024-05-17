package tw.appworks.school.yichien.enabling.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.account.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

    @Query(value = "SELECT * FROM institution WHERE domain_name = :domain", nativeQuery = true)
    Institution getInstitution(@Param("domain") String domain);

    Institution findByDomainName(String domain);

    boolean existsInstitutionByDomainName(String domain);

    @Query(value = "SELECT i.institution_name FROM institution AS i WHERE i.domain_name = :domain", nativeQuery = true)
    String getInstitutionNameByDomain(@Param("domain") String domain);

}

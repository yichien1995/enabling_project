package tw.appworks.school.yichien.enabling.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.account.InstitutionUser;

import java.util.List;

@Repository
public interface InstitutionUserRepository extends JpaRepository<InstitutionUser, Integer> {

	InstitutionUser findInstitutionUserById(Long id);

	@Query(value = "SELECT * FROM institution_user WHERE user_id = :userId", nativeQuery = true)
	List<InstitutionUser> findInstitutionUserByUserId(Long userId);

	@Query(value = "SELECT i.institution_domain FROM institution_user AS i WHERE i.user_id = :userId", nativeQuery = true)
	List<String> getDomainByUserId(@Param("userId") Long userId);

}

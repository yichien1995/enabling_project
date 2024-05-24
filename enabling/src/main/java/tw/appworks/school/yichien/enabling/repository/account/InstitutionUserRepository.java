package tw.appworks.school.yichien.enabling.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.account.InstitutionUser;

@Repository
public interface InstitutionUserRepository extends JpaRepository<InstitutionUser, Long> {

    InstitutionUser findInstitutionUserById(Long id);

//TODO:delete before final release
    //
//	@Query(value = "SELECT * FROM institution_user WHERE user_id = :userId", nativeQuery = true)
//	List<InstitutionUser> findInstitutionUserByUserId(Long userId);
//
//	@Query(value = "SELECT i.institution_domain FROM institution_user AS i WHERE i.user_id = :userId", nativeQuery = true)
//	List<String> getDomainByUserId(@Param("userId") Long userId);
//
//	@Query(value = "SELECT i.id, i.institution_domain, i.user_id, i.role_id, i.employee_id FROM institution_user AS i " +
//			"JOIN users AS u ON i.user_id = u.id WHERE u.email = :email", nativeQuery = true)
//	List<InstitutionUser> getInstitutionUserInfoByEmail(@Param("email") String email);

    boolean existsInstitutionUserByInstitutionDomainAndEmployeeId(Institution InstitutionDomain, Integer EmployeeId);

    @Query(value = "SELECT COUNT(i.id) FROM institution_user AS i JOIN users AS u ON i.user_id = u.id " +
            "WHERE i.institution_domain = :domain AND u.email = :email", nativeQuery = true)
    Integer countInstitutionUserByDomainANDEmail(@Param("domain") String domain, @Param("email") String Email);

    void deleteInstitutionUserById(Long id);

}

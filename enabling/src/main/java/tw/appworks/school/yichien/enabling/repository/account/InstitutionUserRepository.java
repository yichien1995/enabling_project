package tw.appworks.school.yichien.enabling.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.account.InstitutionUser;

@Repository
public interface InstitutionUserRepository extends JpaRepository<InstitutionUser, Integer> {

	InstitutionUser findInstitutionUserById(Long id);

}

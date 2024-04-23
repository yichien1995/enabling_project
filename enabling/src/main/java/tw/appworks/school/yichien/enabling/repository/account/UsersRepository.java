package tw.appworks.school.yichien.enabling.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.account.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	boolean existsByEmail(String email);

	Users findUsersByEmail(String email);

	Users findUsersById(Long id);

}

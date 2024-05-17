package tw.appworks.school.yichien.enabling.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.account.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleById(Integer id);
}

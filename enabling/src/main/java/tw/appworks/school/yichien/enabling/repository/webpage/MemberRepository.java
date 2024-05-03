package tw.appworks.school.yichien.enabling.repository.webpage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.webpage.Member;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query(value = "SELECT * FROM member WHERE institution_domain = :domain", nativeQuery = true)
	List<Member> getAllMemberByDomain(@Param("domain") String domain);

	Member getMemberById(Long id);

	void deleteMemberById(Long id);
}

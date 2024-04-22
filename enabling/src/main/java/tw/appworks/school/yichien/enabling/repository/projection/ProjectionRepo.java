package tw.appworks.school.yichien.enabling.repository.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.dto.account.MemberDTO;

import java.util.List;

@Repository
public class ProjectionRepo {

	@PersistenceContext
	private EntityManager entityManager;

	public List<MemberDTO> getMemberDto(String domain) {
		String query = """
				    SELECT i.id, u.user_name FROM institution_user AS i JOIN users AS u ON i.user_id = u.id WHERE i.institution_domain = '%s';
				""";
		return entityManager.createNativeQuery(query.formatted(domain), MemberDTO.class)
				.getResultList();
	}
}

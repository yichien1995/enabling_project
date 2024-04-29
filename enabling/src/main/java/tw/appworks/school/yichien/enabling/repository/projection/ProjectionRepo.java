package tw.appworks.school.yichien.enabling.repository.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.dto.account.InstitutionUserDto;
import tw.appworks.school.yichien.enabling.dto.account.MemberDto;
import tw.appworks.school.yichien.enabling.dto.account.MyInstitutionDto;
import tw.appworks.school.yichien.enabling.dto.account.UserInfoDto;
import tw.appworks.school.yichien.enabling.dto.management.TeamMemberInfoDto;

import java.util.List;

@Repository
public class ProjectionRepo {

	@PersistenceContext
	private EntityManager entityManager;

	public List<MemberDto> getMemberDto(String domain) {
		String query = """
				    SELECT i.id, u.user_name FROM institution_user AS i JOIN users AS u ON i.user_id = u.id WHERE i.institution_domain = '%s';
				""";
		return entityManager.createNativeQuery(query.formatted(domain), MemberDto.class)
				.getResultList();
	}

	public List<MyInstitutionDto> getMyInstitutionDTO(Long id) {
		String query = """
				    SELECT iu.id, r.role, iu.institution_domain, i.institution_name FROM institution_user AS iu 
				    JOIN institution AS i ON iu.institution_domain = i.domain_name 
				    JOIN role AS r ON iu.role_id = r.id WHERE iu.user_id = %d;
				""";
		return entityManager.createNativeQuery(query.formatted(id), MyInstitutionDto.class).getResultList();
	}

	public UserInfoDto getUserInfoDTO(String email) {
		String query = """
				SELECT u.id, u.user_name, u.email FROM users AS u WHERE u.email = '%s'
				""".formatted(email);
		return (UserInfoDto) entityManager.createNativeQuery(query, UserInfoDto.class)
				.getSingleResult();
	}

	public List<InstitutionUserDto> getInstitutionUserDTO(String email) {
		String query = """
				SELECT i.id, i.institution_domain, i.user_id, i.role_id, i.employee_id FROM institution_user AS i 
				JOIN users AS u ON i.user_id = u.id WHERE u.email = '%s';
				""";
		return entityManager.createNativeQuery(query.formatted(email), InstitutionUserDto.class).getResultList();
	}

	public List<TeamMemberInfoDto> getTeamMemberInfoDTO(String domain) {
		String query = """
				    		SELECT i.id, i.employee_id, u.user_name, u.email, r.name FROM institution_user AS i 
				    		JOIN users AS u ON i.user_id = u.id JOIN role AS r ON i.role_id = r.id 
				    		WHERE institution_domain = '%s'		    
				""";
		return entityManager.createNativeQuery(query.formatted(domain), TeamMemberInfoDto.class).getResultList();
	}
}

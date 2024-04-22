package tw.appworks.school.yichien.enabling.repository.clinial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.appworks.school.yichien.enabling.model.clinial.Evaluation;

import java.util.List;


public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

	@Query(value = "SELECT e.* FROM evaluation AS e " +
			"LEFT JOIN institution_user AS i ON e.institution_user_id = i.id " +
			"WHERE i.institution_domain = :domain " +
			"ORDER BY e.evaluation_date ASC, e.evaluation_time ASC", nativeQuery = true)
	List<Evaluation> getEvaluationsByDomain(@Param("domain") String domain);

	@Query(value = "SELECT e.* FROM evaluation AS e " +
			"LEFT JOIN institution_user AS i ON e.institution_user_id = i.id " +
			"WHERE i.institution_domain = :domain AND e.reserved = 0 " +
			"ORDER BY e.evaluation_date ASC, e.evaluation_time ASC", nativeQuery = true)
	List<Evaluation> getUnreservedEvaluations(@Param("domain") String domain);

	void deleteEvaluationById(long id);
}

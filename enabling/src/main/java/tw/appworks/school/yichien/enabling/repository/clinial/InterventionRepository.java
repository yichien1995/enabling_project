package tw.appworks.school.yichien.enabling.repository.clinial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.clinial.Intervention;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {
	void deleteInterventionById(Long id);
}

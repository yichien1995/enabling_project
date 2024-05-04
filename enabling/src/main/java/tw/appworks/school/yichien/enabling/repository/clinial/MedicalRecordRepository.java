package tw.appworks.school.yichien.enabling.repository.clinial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.clinial.MedicalRecord;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

	@Query(value = "SELECT * FROM medical_record WHERE institution_domain = :domain", nativeQuery = true)
	List<MedicalRecord> getAllMedicalRecordByDomain(@Param("domain") String domain);

	MedicalRecord findMedicalRecordById(Long id);

	void deleteMedicalRecordById(Long id);
}

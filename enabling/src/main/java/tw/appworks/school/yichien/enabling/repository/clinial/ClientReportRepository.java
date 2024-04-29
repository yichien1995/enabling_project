package tw.appworks.school.yichien.enabling.repository.clinial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.clinial.ClientReport;

@Repository
public interface ClientReportRepository extends JpaRepository<ClientReport, Long> {
}

package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.ClientReportForm;

public interface ClientService {

	void saveClientReport(Long institutionUserId, ClientReportForm form);

	void renderClientListPage(Long institutionUserId, String domain, Model model);

	void saveIntervention(Long medicalRecordId, Long institutionUserId);

	void deleteInterventionById(Long id);
}

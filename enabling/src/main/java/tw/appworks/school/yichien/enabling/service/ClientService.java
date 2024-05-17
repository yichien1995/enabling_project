package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.ClientReportForm;

public interface ClientService {

    void renderClientListPage(Long institutionUserId, String domain, Model model);

    void renderClientReportPage(Long institutionUserId, Model model);

    void saveClientReport(Long institutionUserId, ClientReportForm form);

    void updateClientReportById(Long id, ClientReportForm clientReportForm);

    void deleteClientReportById(Long id);

    void saveIntervention(Long medicalRecordId, Long institutionUserId);

    void deleteInterventionById(Long id);
}

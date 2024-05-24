package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.MedicalRecordForm;

public interface ClientManagementService {

    void saveMedicalRecord(String domain, MedicalRecordForm form);

    void updateMedicalRecord(Long id, MedicalRecordForm form);

    void deleteMedicalRecordById(Long id);

    void renderClientManagementPage(String domain, Model model);
}

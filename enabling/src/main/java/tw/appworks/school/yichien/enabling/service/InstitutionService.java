package tw.appworks.school.yichien.enabling.service;

import tw.appworks.school.yichien.enabling.dto.form.NewInstitutionForm;

import java.util.Map;

public interface InstitutionService {

	Map<String, Object> domainErrorMsg(String domain);

	void createNewInstitution(Long userId, NewInstitutionForm form);
}

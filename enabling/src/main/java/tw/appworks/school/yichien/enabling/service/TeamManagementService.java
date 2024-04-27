package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.TeamMemberForm;

import java.util.Map;

public interface TeamManagementService {
	void renderTeamManagementPage(String domain, Model model);

	Map<String, Object> saveInstitutionUser(TeamMemberForm teamMemberForm, String domain);

	void deleteInstitutionUserById(Long id);
}

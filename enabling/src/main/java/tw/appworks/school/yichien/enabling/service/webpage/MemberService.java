package tw.appworks.school.yichien.enabling.service.webpage;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.MemberForm;

public interface MemberService {

	void saveMember(String domain, MemberForm form);

	void renderMemberPage(String domain, Model model);

	void deleteMemberById(Long id);
}

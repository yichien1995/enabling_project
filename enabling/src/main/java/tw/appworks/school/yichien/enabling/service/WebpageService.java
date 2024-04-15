package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Homepage;

public interface WebpageService {

	void renderHomepage(String domain, Model model);

	void getInstitution(String domain, Model model);

	void updateInstitution(String domain, Institution institution);

	void updateHomepage(String domain, HomepageForm homepageForm);

	void getHomepage(String domain,Model model);
	Homepage getHomepageDetail(String domain);

}

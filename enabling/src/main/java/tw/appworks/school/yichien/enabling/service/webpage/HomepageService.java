package tw.appworks.school.yichien.enabling.service.webpage;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Homepage;

public interface HomepageService {

	void renderHomepage(String domain, Model model);

	void renderHomepagePreview(String domain, Model model);

	void getInstitution(String domain, Model model);

	void updateInstitution(String domain, Institution institution);

	void saveHomepage(String domain, HomepageForm homepageForm);

	void saveHomepageDraft(String domain, HomepageForm hf);

	Homepage getHomepageDetail(String domain);

}

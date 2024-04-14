package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Homepage;

public interface WebpageService {

	void renderHomepage(String domain, Model model);

	void getInstitution(String domain, Model model);

	void updateInstitution(String domain, Institution institution);

	void updateStyle(String domain, Integer colorId);

	Homepage getHomepage(String domain);

}

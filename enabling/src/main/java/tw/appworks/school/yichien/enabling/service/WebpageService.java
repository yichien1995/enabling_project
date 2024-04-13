package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Homepage;

public interface WebpageService {

	void renderHomepage(String domain, Model model);
//	Institution getInstitution(String domain);
	Homepage getHomepage(String domain);
}

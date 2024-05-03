package tw.appworks.school.yichien.enabling.service.webpage;

import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.ServicesForm;

public interface ServiceItemService {

	void saveService(String domain, ServicesForm form);

	void renderServicePage(String domain, Model model);

	void updateService(String domain, Long id, ServicesForm form);

	void deleteServiceItemById(Long id);
}

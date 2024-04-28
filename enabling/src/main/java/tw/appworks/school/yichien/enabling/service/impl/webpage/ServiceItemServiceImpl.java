package tw.appworks.school.yichien.enabling.service.impl.webpage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.ServicesForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.ServiceItem;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.repository.webpage.ServiceItemRepository;
import tw.appworks.school.yichien.enabling.service.FileStorageService;
import tw.appworks.school.yichien.enabling.service.webpage.ServiceItemService;

import java.util.List;

@Service
public class ServiceItemServiceImpl implements ServiceItemService {

	private final FileStorageService fileStorageService;

	private final ServiceItemRepository serviceItemRepository;

	private final InstitutionRepository institutionRepository;

	@Value("${prefix.image}")
	private String imageUrlPrefix;

	public ServiceItemServiceImpl(FileStorageService fileStorageService, ServiceItemRepository serviceItemRepository, InstitutionRepository institutionRepository) {
		this.fileStorageService = fileStorageService;
		this.serviceItemRepository = serviceItemRepository;
		this.institutionRepository = institutionRepository;
	}

	public void saveService(String domain, ServicesForm form) {
		ServiceItem serviceItem = new ServiceItem();
		Institution institution = institutionRepository.getInstitution(domain);

		// save image relative URL
		String uploadAndGetPath = fileStorageService.uploadFile(domain, form.getTitle(), form.getImage());
		serviceItem.setImage(uploadAndGetPath);

		serviceItem.setTitle(form.getTitle());
		serviceItem.setPrice(form.getPrice());
		serviceItem.setInstitutionDomain(institution);

		serviceItemRepository.save(serviceItem);
	}

	@Override
	public void renderServicePage(String domain, Model model) {
		List<ServiceItem> serviceItems = serviceItemRepository.getAllServicesItemByDomain(domain);
		for (ServiceItem item : serviceItems) {
			item.setPrice(item.getPrice().replace("\n", "<br>"));
			item.setImage(imageUrlPrefix + item.getImage());
		}
		model.addAttribute("serviceItems", serviceItems);
	}

	@Override
	@Transactional
	public void deleteServiceItemById(Long id) {
		serviceItemRepository.deleteServiceItemById(id);
	}
}

package tw.appworks.school.yichien.enabling.service.impl.webpage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import tw.appworks.school.yichien.enabling.service.impl.S3UploadServiceImpl;
import tw.appworks.school.yichien.enabling.service.webpage.ServiceItemService;

import java.util.List;

@Service
public class ServiceItemServiceImpl implements ServiceItemService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceItemServiceImpl.class);
    private final FileStorageService fileStorageService;
    private final S3UploadServiceImpl s3UploadService;
    private final ServiceItemRepository serviceItemRepository;
    private final InstitutionRepository institutionRepository;
    @Value("${prefix.image}")
    private String imageUrlPrefix;

    public ServiceItemServiceImpl(FileStorageService fileStorageService, S3UploadServiceImpl s3UploadService, ServiceItemRepository serviceItemRepository, InstitutionRepository institutionRepository) {
        this.fileStorageService = fileStorageService;
        this.s3UploadService = s3UploadService;
        this.serviceItemRepository = serviceItemRepository;
        this.institutionRepository = institutionRepository;
    }

    public void saveService(String domain, ServicesForm form) {
        Institution institution = institutionRepository.getInstitution(domain);

        // save image relative URL
//        String uploadAndGetPath = fileStorageService.uploadFile(domain, form.getTitle(), form.getImage());
        String uploadAndGetPath = s3UploadService.uploadFileToS3(domain, form.getImage().getOriginalFilename(), form.getImage());

        serviceItemRepository.save(ServiceItem.convertNewForm(form, institution, uploadAndGetPath));
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
    public void updateService(String domain, Long id, ServicesForm form) {
        ServiceItem serviceItem = serviceItemRepository.getServiceItemById(id);

        if (!form.getImage().isEmpty()) {
//            String uploadAndGetPath = fileStorageService.uploadFile(domain, form.getTitle(), form.getImage());
            String uploadAndGetPath = s3UploadService.uploadFileToS3(domain, form.getImage().getOriginalFilename(), form.getImage());
            serviceItem.setImage(uploadAndGetPath);
        }

        serviceItemRepository.save(ServiceItem.convertUpdateForm(form, serviceItem));
    }

    @Override
    @Transactional
    public void deleteServiceItemById(Long id) {
        serviceItemRepository.deleteServiceItemById(id);
    }
}

package tw.appworks.school.yichien.enabling.service.impl.webpage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.dto.webpage.LocationDto;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Homepage;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.repository.webpage.HomepageRepository;
import tw.appworks.school.yichien.enabling.service.FileStorageService;
import tw.appworks.school.yichien.enabling.service.google.GeocodingServiceImpl;
import tw.appworks.school.yichien.enabling.service.impl.S3UploadServiceImpl;
import tw.appworks.school.yichien.enabling.service.webpage.HomepageService;

@Service
public class HomepageServiceImpl implements HomepageService {

    private static final Logger logger = LoggerFactory.getLogger(HomepageServiceImpl.class);
    private final InstitutionRepository institutionRepository;
    private final HomepageRepository homepageRepository;
    private final FileStorageService fileStorageService;
    private final GeocodingServiceImpl geocodingService;
    private final S3UploadServiceImpl s3UploadService;

    @Value("${prefix.image}")
    private String IMAGE_URL_PREFIX;

    @Value("${google.api.key}")
    private String API_KEY;

    @Value("${default.logo.relative.path}")
    private String DEFAULT_LOGO_PATH;

    @Value("${default.main_image.relative.path}")
    private String DEFAULT_MAIN_IMAGE_PATH;

    public HomepageServiceImpl(InstitutionRepository institutionRepository,
                               HomepageRepository homepageRepository, FileStorageService fileStorageService, GeocodingServiceImpl geocodingService, S3UploadServiceImpl s3UploadService) {
        this.institutionRepository = institutionRepository;
        this.homepageRepository = homepageRepository;
        this.fileStorageService = fileStorageService;
        this.geocodingService = geocodingService;
        this.s3UploadService = s3UploadService;
    }

    // functions of rendering homepage
    @Override
    public void renderHomePage(String domain, Model model) {
        Homepage homepage = findExistHomepage(domain);
        renderHomePageContent(model, homepage);
    }

    @Override
    public void renderHomepagePreview(String domain, Model model) {
        Homepage homepage = homepageRepository.getHomepage(domain, 0);
        renderHomePageContent(model, homepage);
    }

    @Override
    public void renderHeaderAndFooter(String domain, Model model) {
        Homepage homepage = findExistHomepage(domain);
        addHomepageModelAttr(homepage, model);
    }

    private void renderHomePageContent(Model model, Homepage homepage) {
        String address = homepage.getInstitutionDomain().getAddress();
        LocationDto location = geocodingService.getGeocodingResponse(address);
        model.addAttribute("location", location);
        model.addAttribute("key", API_KEY);
        addHomepageModelAttr(homepage, model);
    }

    private Homepage findExistHomepage(String domain) {
        Homepage homepage = homepageRepository.getHomepage(domain, 1);
        if (homepage == null) {
            homepage = homepageRepository.getHomepage(domain, 0);
        }
        return homepage;
    }


    private void addHomepageModelAttr(Homepage homepage, Model model) {
        model.addAttribute("homePage", homepage);

        // add prefix to image url
        model.addAttribute("logoImage", IMAGE_URL_PREFIX + homepage.getLogo());
        model.addAttribute("mainImage", IMAGE_URL_PREFIX + homepage.getMainImage());

        // replace "\n" to "<br>"
        addFormattedAttribute(model, "businessHour", homepage.getInstitutionDomain().getBusinessHour());
        addFormattedAttribute(model, "imageDescription", homepage.getImageDescription());
        addFormattedAttribute(model, "institutionIntro", homepage.getInstitutionIntro());
    }

    private void addFormattedAttribute(Model model, String attributeName, String attributeValue) {
        if (attributeValue != null) {
            model.addAttribute(attributeName, attributeValue.replace("\n", "<br>"));
        }
    }


    // functions of saving / updating homepage form
    @Override
    @Transactional
    public void saveHomepage(String domain, HomepageForm hf) {
        if (checkHomepageExists(domain, 1)) {
            Homepage existData = homepageRepository.getHomepage(domain, 1);
            saveHomepageForm(domain, "public", hf, existData);
        } else {
            Institution institution = institutionRepository.findByDomainName(domain);
            Homepage homepage = new Homepage();

            // create new public homepage
            homepage.setStatus(1);
            homepage.setInstitutionDomain(institution);
            homepage.setLogo(DEFAULT_LOGO_PATH);
            homepage.setMainImage(DEFAULT_MAIN_IMAGE_PATH);
            homepageRepository.save(homepage);

            // save homepage form
            saveHomepageForm(domain, "public", hf, homepage);
        }
    }

    @Override
    public void saveHomepageDraft(String domain, HomepageForm hf) {
        Homepage existData = homepageRepository.getHomepage(domain, 0);
        saveHomepageForm(domain, "draft", hf, existData);
    }

    private void saveHomepageForm(String domain, String fileType, HomepageForm form, Homepage homepage) {
        if (!form.getLogo().isEmpty()) {
            String logoPath = fileStorageService.uploadFile(domain, "logo_" + fileType, form.getLogo());
//            String logoPath = s3UploadService.uploadFileToS3(domain, hf.getLogo().getOriginalFilename() + fileType, hf.getLogo());
            homepage.setLogo(logoPath);
        }

        if (!form.getMainImage().isEmpty()) {
            String mainImagePath = fileStorageService.uploadFile(domain, "main_" + fileType, form.getMainImage());
//            String mainImagePath = s3UploadService.uploadFileToS3(domain, hf.getMainImage().getOriginalFilename() + fileType, hf.getMainImage());
            homepage.setMainImage(mainImagePath);
        }
        homepageRepository.save(Homepage.convertForm(form, homepage));
    }

    private boolean checkHomepageExists(String domain, int status) {
        return homepageRepository.checkHomepage(domain, status) == 1;
    }

    //TODO: Move to institutionService
    @Override
    public void getInstitution(String domain, Model model) {
        Institution institutionInfo = institutionRepository.getInstitution(domain);
        model.addAttribute("i", institutionInfo);
        addFormattedAttribute(model, "businessHour", institutionInfo.getBusinessHour());

    }

    @Override
    public void updateInstitution(String domain, Institution form) {
        Institution institution = institutionRepository.findByDomainName(domain);
        institutionRepository.save(Institution.convertUpdateForm(form, institution));
    }

}

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
import tw.appworks.school.yichien.enabling.model.webpage.ThemeColor;
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
	private String imageUrlPrefix;
	@Value("${google.api.key}")
	private String API_KEY;

	public HomepageServiceImpl(InstitutionRepository institutionRepository,
	                           HomepageRepository homepageRepository, FileStorageService fileStorageService, GeocodingServiceImpl geocodingService, S3UploadServiceImpl s3UploadService) {
		this.institutionRepository = institutionRepository;
		this.homepageRepository = homepageRepository;
		this.fileStorageService = fileStorageService;
		this.geocodingService = geocodingService;
		this.s3UploadService = s3UploadService;
	}

	@Override
	public void renderHomePage(String domain, Model model) {
		Homepage homepage;
		if (homepageRepository.getHomepage(domain, 1) != null) {
			homepage = homepageRepository.getHomepage(domain, 1);
		} else {
			homepage = homepageRepository.getHomepage(domain, 0);
		}
		String address = homepage.getInstitutionDomain().getAddress();
		LocationDto location = geocodingService.getGeocodingResponse(address);
		addHomepageModelAttr(homepage, model);
		model.addAttribute("location", location);
		model.addAttribute("key", API_KEY);
	}

	@Override
	public void renderHeaderAndFooter(String domain, Model model) {
		Homepage homepage;
		if (homepageRepository.getHomepage(domain, 1) != null) {
			homepage = homepageRepository.getHomepage(domain, 1);
		} else {
			homepage = homepageRepository.getHomepage(domain, 0);
		}
		addHomepageModelAttr(homepage, model);
	}

	@Override
	public void renderHomepagePreview(String domain, Model model) {
		Homepage homepage = homepageRepository.getHomepage(domain, 0);
		String address = homepage.getInstitutionDomain().getAddress();
		LocationDto location = geocodingService.getGeocodingResponse(address);
		addHomepageModelAttr(homepage, model);
		model.addAttribute("location", location);
		model.addAttribute("key", API_KEY);
	}

	private void addHomepageModelAttr(Homepage homepage, Model model) {
		model.addAttribute("homePage", homepage);

		// add prefix to image url
		model.addAttribute("logoImage", imageUrlPrefix + homepage.getLogo());
		model.addAttribute("mainImage", imageUrlPrefix + homepage.getMainImage());

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

	@Override
	public void getInstitution(String domain, Model model) {
		Institution institutionInfo = institutionRepository.getInstitution(domain);
		model.addAttribute("i", institutionInfo);
		addFormattedAttribute(model, "businessHour", institutionInfo.getBusinessHour());

	}


	@Override
	public void updateInstitution(String domain, Institution i) {
		Institution existData = institutionRepository.findByDomainName(domain);

		existData.setInstitutionName(i.getInstitutionName());
		existData.setAddress(i.getAddress());
		existData.setTel(i.getTel());
		existData.setBusinessHour(i.getBusinessHour());

		institutionRepository.save(existData);
	}

	@Override
	@Transactional
	public void saveHomepage(String domain, HomepageForm hf) {
		if (checkHomepageExists(domain, 1)) {
			Homepage existData = homepageRepository.getHomepage(domain, 1);
			saveHomepageForm(domain, "public", hf, existData);
		} else {
			Institution institution = institutionRepository.findByDomainName(domain);
			Homepage homepage = new Homepage();

			homepage.setStatus(1);
			homepage.setInstitutionDomain(institution);
			homepageRepository.save(homepage);
			saveHomepageForm(domain, "public", hf, homepage);
		}
	}

	@Override
	public void saveHomepageDraft(String domain, HomepageForm hf) {
		Homepage existData = homepageRepository.getHomepage(domain, 0);
		saveHomepageForm(domain, "draft", hf, existData);
	}

	public void saveHomepageForm(String domain, String fileType, HomepageForm hf, Homepage homepage) {
		homepage.setImageDescription(hf.getImageDescription());
		homepage.setInstitutionIntro(hf.getInstitutionIntro());

		ThemeColor newColor = new ThemeColor();
		newColor.setId(hf.getColor());

		String logoPath = fileStorageService.uploadFile(domain, "logo_" + fileType, hf.getLogo());
		String mainImagePath = fileStorageService.uploadFile(domain, "main_" + fileType, hf.getMainImage());

//		String logoPath = s3UploadService.uploadFileToS3(domain, "logo_" + fileType, hf.getLogo());
//		String mainImagePath = s3UploadService.uploadFileToS3(domain, "main_" + fileType, hf.getMainImage());

		homepage.setLogo(logoPath);
		homepage.setMainImage(mainImagePath);

		homepage.setThemeColorId(newColor);
		homepageRepository.save(homepage);
	}

	private boolean checkHomepageExists(String domain, int status) {
		return homepageRepository.checkHomepage(domain, status) == 1;
	}


	@Override
	public Homepage getHomepageDetail(String domain) {
		return homepageRepository.getHomepage(domain, 1);
	}

	;


}

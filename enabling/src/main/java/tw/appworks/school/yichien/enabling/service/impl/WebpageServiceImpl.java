package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Homepage;
import tw.appworks.school.yichien.enabling.model.webpage.ThemeColor;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.repository.webpage.HomepageRepository;
import tw.appworks.school.yichien.enabling.service.WebpageService;

@Service
public class WebpageServiceImpl implements WebpageService {

	private final InstitutionRepository institutionRepository;

	private final HomepageRepository homepageRepository;

	public WebpageServiceImpl(InstitutionRepository institutionRepository,
	                          HomepageRepository homepageRepository) {
		this.institutionRepository = institutionRepository;
		this.homepageRepository = homepageRepository;
	}

	@Override
	public void renderHomepage(String domain, Model model) {
//		Institution institution = getInstitution(domain);
//		model.addAttribute("institution",institution);
		Homepage homepage = getHomepageDetail(domain);
		model.addAttribute("homePage", homepage);
		String businessHour = homepage.getInstitutionDomain().getBusinessHour().replace("\n", "<br>");
		model.addAttribute("business_hour", businessHour);
		String imageDescription = homepage.getImageDescription().replace("\n", "<br>");
		model.addAttribute("imageDescription", imageDescription);
		String institutionIntro = homepage.getInstitutionIntro().replace("\n", "<br>");
		model.addAttribute("institutionIntro", institutionIntro);
	}


	@Override
	public void getInstitution(String domain, Model model) {
		Institution institutionInfo = institutionRepository.getInstitution(domain);
		model.addAttribute("i", institutionInfo);

		String businessHour = institutionInfo.getBusinessHour().replace("\n", "<br>");
		model.addAttribute("business_hour", businessHour);

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

	public void getHomepage(String domain, Model model) {
		Homepage homepage = homepageRepository.getHomepage(domain);
		model.addAttribute("h", homepage);
	}


	@Override
	public void updateHomepage(String domain, HomepageForm h) {
		Homepage existData = homepageRepository.getHomepage(domain);
		existData.setImageDescription(h.getImageDescription());
		existData.setInstitutionIntro(h.getInstitutionIntro());

		ThemeColor newColor = new ThemeColor();
		newColor.setId(h.getColor());


		//上傳圖片修改
//		existData.setLogo(h.getLogo());
//		existData.setMainImage(h.getMainImage());

		existData.setThemeColorId(newColor);
		homepageRepository.save(existData);
	}


	@Override
	public Homepage getHomepageDetail(String domain) {
		return homepageRepository.getHomepage(domain);
	}

	;


}

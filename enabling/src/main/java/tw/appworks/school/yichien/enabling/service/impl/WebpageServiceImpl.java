package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
		Homepage homepage = getHomepage(domain);
		model.addAttribute("homePage", homepage);
		String businessHour = homepage.getInstitutionDomain().getBusinessHour().replace("\n", "<br>");

		model.addAttribute("business_hour", businessHour);

	}

	;

	@Override
	public void getInstitution(String domain, Model model) {
		Institution institutionInfo = institutionRepository.getInstitution(domain);
		model.addAttribute("i", institutionInfo);

		String businessHour = institutionInfo.getBusinessHour().replace("\n", "<br>");
		model.addAttribute("business_hour", businessHour);

	}

	;

	public void updateInstitution(String domain, Institution i) {
		Institution existData = institutionRepository.findByDomainName(domain);

		existData.setInstitutionName(i.getInstitutionName());
		existData.setAddress(i.getAddress());
		existData.setTel(i.getTel());
		existData.setBusinessHour(i.getBusinessHour());

		institutionRepository.save(existData);
	}

	;

	@Override
	public void updateStyle(String domain, Integer colorId) {
		Homepage existData = homepageRepository.getHomepage(domain);
		ThemeColor newColor = new ThemeColor();
		newColor.setId(colorId);

		existData.setThemeColorId(newColor);
		homepageRepository.save(existData);
	}

	@Override
	public Homepage getHomepage(String domain) {
		return homepageRepository.getHomepage(domain);
	}

	;

}

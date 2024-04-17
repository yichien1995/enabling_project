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
		addHomepageModelAttr(homepage, model);
	}

	private void addHomepageModelAttr(Homepage homepage, Model model) {
		model.addAttribute("homePage", homepage);
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
		addFormattedAttribute(model,"businessHour",institutionInfo.getBusinessHour());

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
	public void saveHomepage(String domain, HomepageForm hf) {
		Homepage existData = homepageRepository.getHomepage(domain, 1);
		saveHomepageForm(domain, hf, existData);
	}

	@Override
	public void saveHomepageDraft(String domain, HomepageForm hf) {
		Homepage existData = homepageRepository.getHomepage(domain, 0);
		saveHomepageForm(domain, hf, existData);
	}

	public void saveHomepageForm(String domain, HomepageForm hf, Homepage homepage) {
		homepage.setImageDescription(hf.getImageDescription());
		homepage.setInstitutionIntro(hf.getInstitutionIntro());

		ThemeColor newColor = new ThemeColor();
		newColor.setId(hf.getColor());


		//上傳圖片修改
//		homepage.setLogo(hf.getLogo());
//		homepage.setMainImage(hf.getMainImage());

		homepage.setThemeColorId(newColor);
		homepageRepository.save(homepage);
	}


	@Override
	public Homepage getHomepageDetail(String domain) {
		return homepageRepository.getHomepage(domain, 1);
	}

	;


}

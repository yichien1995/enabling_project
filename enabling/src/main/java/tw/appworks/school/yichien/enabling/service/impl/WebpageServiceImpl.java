package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.webpage.Homepage;
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
	public void renderHomepage(String domain, Model model){
//		Institution institution = getInstitution(domain);
//		model.addAttribute("institution",institution);
		Homepage homepage = getHomepage(domain);
		model.addAttribute("homePage",homepage);
	};
//	@Override
//	public Institution getInstitution(String domain){
//		return institutionRepository.getInstitution(domain);
//	};

	@Override
	public Homepage getHomepage(String domain){
		return homepageRepository.getHomepage(domain);
	};

}

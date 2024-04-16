package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
import tw.appworks.school.yichien.enabling.dto.account.HomepagePreviewDTO;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.model.webpage.ThemeColor;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.repository.webpage.ThemeColorRepository;
import tw.appworks.school.yichien.enabling.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	private final InstitutionRepository institutionRepository;

	private final ThemeColorRepository themeColorRepository;

	public AdminServiceImpl(InstitutionRepository institutionRepository,
	                        ThemeColorRepository themeColorRepository) {
		this.institutionRepository = institutionRepository;
		this.themeColorRepository = themeColorRepository;
	}

	@Override
	public HomepagePreviewDTO setPreviewData(HomepageForm h) {
		HomepagePreviewDTO data = new HomepagePreviewDTO();
		//上傳圖片這兩行記得改掉
		data.setLogo(h.getLogo());
		data.setMainImage(h.getMainImage());
		//

		data.setImageDescription(h.getImageDescription().replace("\n", "<br>"));
		data.setInstitutionIntro(h.getInstitutionIntro().replace("\n", "<br>"));

		//get and set color code
		int themeColorId = h.getColor();
		ThemeColor t = themeColorRepository.findById(themeColorId);
		data.setBackgroundColor(t.getBackgroundColor());
		data.setHeadingColor(t.getHeadingColor());

		return data;
	}

	@Override
	public boolean checkDomain(String domain) {
		return institutionRepository.existsInstitutionByDomainName(domain);
	}

	;

}

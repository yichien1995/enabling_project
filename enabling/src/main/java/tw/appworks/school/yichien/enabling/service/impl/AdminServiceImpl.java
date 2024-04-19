package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
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
	public boolean checkDomain(String domain) {
		return institutionRepository.existsInstitutionByDomainName(domain);
	}

	;

}

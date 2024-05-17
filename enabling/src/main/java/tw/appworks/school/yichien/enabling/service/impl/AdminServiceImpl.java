package tw.appworks.school.yichien.enabling.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private final InstitutionRepository institutionRepository;

    public AdminServiceImpl(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @Override
    public boolean checkDomain(String domain) {
        return institutionRepository.existsInstitutionByDomainName(domain);
    }

    @Override
    public void renderAdminSidebar(String domain, Model model) {
        String institutionName = institutionRepository.getInstitutionNameByDomain(domain);

        model.addAttribute("institutionName", institutionName);
        model.addAttribute("domain", domain);
    }

}

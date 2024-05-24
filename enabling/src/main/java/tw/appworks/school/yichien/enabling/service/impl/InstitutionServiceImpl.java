package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.appworks.school.yichien.enabling.dto.form.NewInstitutionForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.account.InstitutionUser;
import tw.appworks.school.yichien.enabling.model.account.Role;
import tw.appworks.school.yichien.enabling.model.account.Users;
import tw.appworks.school.yichien.enabling.model.webpage.Homepage;
import tw.appworks.school.yichien.enabling.model.webpage.ThemeColor;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionUserRepository;
import tw.appworks.school.yichien.enabling.repository.account.RoleRepository;
import tw.appworks.school.yichien.enabling.repository.account.UsersRepository;
import tw.appworks.school.yichien.enabling.repository.webpage.HomepageRepository;
import tw.appworks.school.yichien.enabling.repository.webpage.ThemeColorRepository;
import tw.appworks.school.yichien.enabling.service.InstitutionService;

import java.util.HashMap;
import java.util.Map;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;

    private final InstitutionUserRepository institutionUserRepository;

    private final UsersRepository usersRepository;

    private final RoleRepository roleRepository;

    private final ThemeColorRepository themeColorRepository;

    private final HomepageRepository homepageRepository;

    @Value("${default.logo.relative.path}")
    private String DEFAULT_LOGO_PATH;

    @Value("${default.main_image.relative.path}")
    private String DEFAULT_MAIN_IMAGE_PATH;


    public InstitutionServiceImpl(InstitutionRepository institutionRepository,
                                  InstitutionUserRepository institutionUserRepository,
                                  UsersRepository usersRepository, RoleRepository roleRepository,
                                  ThemeColorRepository themeColorRepository,
                                  HomepageRepository homepageRepository) {
        this.institutionRepository = institutionRepository;
        this.institutionUserRepository = institutionUserRepository;
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.themeColorRepository = themeColorRepository;
        this.homepageRepository = homepageRepository;
    }

    @Override
    @Transactional
    public void createNewInstitution(Long userId, NewInstitutionForm form) {
        saveInstitution(form);
        saveInstitutionUser(userId, form.getInstitutionDomain());
        saveDefaultHomepage(form.getInstitutionDomain());
    }

    @Override
    @Transactional
    public void deleteInstitutionById(Integer id) {
        institutionRepository.deleteInstitutionById(id);
    }

    private void saveInstitution(NewInstitutionForm form) {
        institutionRepository.save(Institution.convertNewForm(form));
    }

    private void saveInstitutionUser(Long userId, String domain) {
        InstitutionUser institutionUser = new InstitutionUser();
//		// set FK
        Institution institution = institutionRepository.getInstitution(domain);
        Users user = usersRepository.findUsersById(userId);
        Role role = roleRepository.findRoleById(1);

        institutionUser.setInstitutionDomain(institution);
        institutionUser.setUserId(user);
        institutionUser.setRoleId(role);
        institutionUser.setEmployeeId(1);

        institutionUserRepository.save(institutionUser);
    }

    private void saveDefaultHomepage(String domain) {
        Homepage homepage = new Homepage();
        Institution institution = institutionRepository.getInstitution(domain);
        ThemeColor themeColor = themeColorRepository.findById(1);

        homepage.setThemeColorId(themeColor);
        homepage.setLogo(DEFAULT_LOGO_PATH);
        homepage.setMainImage(DEFAULT_MAIN_IMAGE_PATH);
        homepage.setImageDescription("輸入主要圖片敘述");
        homepage.setInstitutionIntro("輸入關於我們敘述");
        homepage.setInstitutionDomain(institution);
        homepage.setStatus(0);
        homepageRepository.save(homepage);
    }


    @Override
    public Map<String, Object> domainErrorMsg(String domain) {
        Map<String, Object> errorMsg = new HashMap<>();
        if (!checkDomainValidation(domain)) {
            errorMsg.put("error", "請輸入20字內大小寫英文字母與數字組合");
            return errorMsg;
        }

        if (institutionRepository.existsInstitutionByDomainName(domain)) {
            errorMsg.put("error", "該網域已存在");
            return errorMsg;
        }
        return null;
    }

    private boolean checkDomainValidation(String domain) {
        if (domain.length() > 20)
            return false;

        if (!domain.matches("[a-zA-Z0-9]+"))
            return false;

        return true;
    }
}

package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.account.MyInstitutionDto;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionUserRepository;
import tw.appworks.school.yichien.enabling.repository.projection.ProjectionRepo;
import tw.appworks.school.yichien.enabling.service.MainPageService;

import java.util.List;

@Service
public class MainPageServiceImpl implements MainPageService {

    private final InstitutionUserRepository institutionUserRepository;

    private final ProjectionRepo projectionRepo;

    public MainPageServiceImpl(InstitutionUserRepository institutionUserRepository, ProjectionRepo projectionRepo) {
        this.institutionUserRepository = institutionUserRepository;
        this.projectionRepo = projectionRepo;
    }

    @Override
    public void renderMyInstitutionPage(Model model, Long id) {
        List<MyInstitutionDto> myInstitutions = projectionRepo.getMyInstitutionDTO(id);
        for (MyInstitutionDto data : myInstitutions) {
            data.setBusinessHour(data.getBusinessHour().replace("\n", "<br>"));
        }
        model.addAttribute("myInstitutions", myInstitutions);
    }
}

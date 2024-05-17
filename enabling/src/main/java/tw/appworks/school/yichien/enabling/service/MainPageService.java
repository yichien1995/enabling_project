package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;

public interface MainPageService {
    void renderMyInstitutionPage(Model model, Long id);
}

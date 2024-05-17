package tw.appworks.school.yichien.enabling.service;

import org.springframework.ui.Model;

public interface ReportService {

    void renderReportPage(String domain, Model model);
}

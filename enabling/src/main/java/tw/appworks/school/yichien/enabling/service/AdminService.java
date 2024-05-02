package tw.appworks.school.yichien.enabling.service;


import org.springframework.ui.Model;

public interface AdminService {

	boolean checkDomain(String domain);

	void renderAdminSidebar(String domain, Model model);
}

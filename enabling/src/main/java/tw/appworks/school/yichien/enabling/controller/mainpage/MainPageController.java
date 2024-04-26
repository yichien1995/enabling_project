package tw.appworks.school.yichien.enabling.controller.mainpage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import tw.appworks.school.yichien.enabling.service.MainPageService;
import tw.appworks.school.yichien.enabling.service.impl.SessionServiceImpl;

@Controller
public class MainPageController {

	private final MainPageService mainPageService;

	private final SessionServiceImpl sessionService;

	@Value("${prefix.domain}")
	private String domainPrefix;

	public MainPageController(MainPageService mainPageService, SessionServiceImpl sessionService) {
		this.mainPageService = mainPageService;
		this.sessionService = sessionService;
	}

	@GetMapping
	public String index() {
		return "main_page/index";
	}

	@GetMapping("/register")
	public String register() {
		return "main_page/register_login";
	}

	@GetMapping("/myinstitution")
	public String user(Model model, @CookieValue(value = "enabling", required = false) String sessionID) {
		if (sessionID == null) {
			return "redirect:" + domainPrefix;
		}
		long userId = sessionService.getUserInfoDTOFromSession(sessionID).getUserId();
		mainPageService.renderMyInstitutionPage(model, userId);
		return "main_page/my_institution";
	}

	@GetMapping("/myinstitution/create")
	public String createInstitutionPage() {
		return "main_page/create_institution";
	}
}
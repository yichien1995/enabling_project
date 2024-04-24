package tw.appworks.school.yichien.enabling.controller.mainpage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tw.appworks.school.yichien.enabling.service.MainPageService;

@Controller
public class MainPageController {

	private final MainPageService mainPageService;

	public MainPageController(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
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
	public String user(Model model) {
		// user id 從 session 中取得
		mainPageService.renderMyInstitutionPage(model, 5L);
		return "main_page/my_institution";
	}

	@GetMapping("/myinstitution/create")
	public String createInstitutionPage() {
		return "main_page/create_institution";
	}
}

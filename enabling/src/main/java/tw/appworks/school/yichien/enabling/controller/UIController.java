package tw.appworks.school.yichien.enabling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {

	@GetMapping("/ui")
	public String uiTest() {
		return "fragment/ui";
	}
}

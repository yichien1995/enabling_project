package tw.appworks.school.yichien.enabling.controller.therapist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/therapist/{domain}")
public class TherapistController {
	@GetMapping
	public String therapistMainPage(@PathVariable String domain, Model model) {
		model.addAttribute("domain", domain);
		return "therapist/therapist";
	}
}

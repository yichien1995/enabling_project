package tw.appworks.school.yichien.enabling.controller.therapist;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.appworks.school.yichien.enabling.service.ClientService;
import tw.appworks.school.yichien.enabling.service.TherapistService;
import tw.appworks.school.yichien.enabling.service.impl.SessionServiceImpl;

@Controller
@RequestMapping("/therapist/{domain}")
public class TherapistController {

	private final ClientService clientService;

	private final SessionServiceImpl sessionService;

	private final TherapistService therapistService;

	public TherapistController(ClientService clientService, SessionServiceImpl sessionService, TherapistService therapistService) {
		this.clientService = clientService;
		this.sessionService = sessionService;
		this.therapistService = therapistService;
	}

//	@GetMapping
//	public String therapistMainPage(@PathVariable String domain, Model model) {
//		model.addAttribute("domain", domain);
//		return "therapist/therapist";
//	}

	@GetMapping
	public String therapistMainPage(@PathVariable String domain, Model model,
	                                @CookieValue(value = "enabling", required = false) String sessionID)
			throws JsonProcessingException {
		Long institutionUserId = sessionService.getInstitutionUserIdFromSession(sessionID, domain);
		model.addAttribute("domain", domain);
		therapistService.renderAdminSidebar(domain, model);
		clientService.renderClientListPage(institutionUserId, domain, model);
		return "therapist/client_list";
	}

	@GetMapping("/client/report")
	public String clientReportPage(@PathVariable String domain, Model model,
	                               @CookieValue(value = "enabling", required = false) String sessionID)
			throws JsonProcessingException {
		Long institutionUserId = sessionService.getInstitutionUserIdFromSession(sessionID, domain);
		therapistService.renderAdminSidebar(domain, model);
		clientService.renderClientReportPage(institutionUserId, model);
//		model.addAttribute("domain", domain);
		return "therapist/client_report";
	}

	@GetMapping("/client/list")
	public String clientListPage(@PathVariable String domain, Model model,
	                             @CookieValue(value = "enabling", required = false) String sessionID)
			throws JsonProcessingException {

		Long institutionUserId = sessionService.getInstitutionUserIdFromSession(sessionID, domain);
//		model.addAttribute("domain", domain);
		therapistService.renderAdminSidebar(domain, model);
		clientService.renderClientListPage(institutionUserId, domain, model);
		return "therapist/client_list";
	}
}

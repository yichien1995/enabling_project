package tw.appworks.school.yichien.enabling.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.NewInstitutionForm;
import tw.appworks.school.yichien.enabling.service.InstitutionService;
import tw.appworks.school.yichien.enabling.service.impl.SessionServiceImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/institution")
public class InstitutionController {

	private final InstitutionService institutionService;

	private final SessionServiceImpl sessionService;

	public InstitutionController(InstitutionService institutionService, SessionServiceImpl sessionService) {
		this.institutionService = institutionService;
		this.sessionService = sessionService;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createNewInstitution(@ModelAttribute NewInstitutionForm form,
	                                              @CookieValue(value = "enabling") String sessionID) {
		Map<String, Object> domainErrorMsg = institutionService.domainErrorMsg(form.getInstitutionDomain());
		if (domainErrorMsg != null) {
			return ResponseEntity.badRequest().body(domainErrorMsg);
		}
		// get userId from session
		long userId = sessionService.getUserInfoDTOFromSession(sessionID).getUserId();
		institutionService.createNewInstitution(userId, form);
		Map<String, Object> result = new HashMap<>();
		result.put("success", "Create institution successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}

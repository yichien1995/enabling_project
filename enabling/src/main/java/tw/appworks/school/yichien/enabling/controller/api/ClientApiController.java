package tw.appworks.school.yichien.enabling.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.ClientReportForm;
import tw.appworks.school.yichien.enabling.service.ClientService;
import tw.appworks.school.yichien.enabling.service.impl.SessionServiceImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/therapist/{domain}/client")
public class ClientApiController {

	public static final Logger logger = LoggerFactory.getLogger(ClientApiController.class);
	@Autowired
	private final SessionServiceImpl sessionService;
	private final ClientService clientService;


	public ClientApiController(SessionServiceImpl sessionService, ClientService clientService) {
		this.sessionService = sessionService;
		this.clientService = clientService;
	}

	@PostMapping("/report")
	public ResponseEntity<?> reportTotalAttendance(@PathVariable String domain,
	                                               @ModelAttribute ClientReportForm clientReportForm,
	                                               @CookieValue(value = "enabling", required = false) String sessionID)
			throws JsonProcessingException {
		Map<String, Object> result = new HashMap<>();
		Long institutionUserId = sessionService.getInstitutionUserIdFromSession(sessionID, domain);
		if (institutionUserId == null) {
			return handleForbiddenRequest(result);
		}

		clientService.saveClientReport(institutionUserId, clientReportForm);
		result.put("success", "Report client total attendance successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PostMapping("/list")
	public ResponseEntity<?> addClientList(@PathVariable String domain,
	                                       @RequestBody Map<String, Object> data,
	                                       @CookieValue(value = "enabling", required = false) String sessionID)
			throws JsonProcessingException {

		Map<String, Object> result = new HashMap<>();
		Long medicalRecordId = ((Integer) data.get("medical_record_id")).longValue();
		Long institutionUserId = sessionService.getInstitutionUserIdFromSession(sessionID, domain);
		if (institutionUserId == null) {
			return handleForbiddenRequest(result);
		}
		clientService.saveIntervention(medicalRecordId, institutionUserId);
		result.put("success", "Add client to list successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@DeleteMapping("/list/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteIntervention(@PathVariable String id, @PathVariable String domain) {
		long idValue = Long.parseLong(id);
		clientService.deleteInterventionById(idValue);
		return ResponseEntity.ok("Delete member id: " + id);
	}

	private ResponseEntity<?> handleForbiddenRequest(Map<String, Object> result) {
		result.put("error", "Find no institution user id");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
	}
}

package tw.appworks.school.yichien.enabling.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.MedicalRecordForm;
import tw.appworks.school.yichien.enabling.service.ClientManagementService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/admin/{domain}/management/client")
public class ClientManagementController {

	private final ClientManagementService clientManagementService;

	public ClientManagementController(ClientManagementService clientManagementService) {
		this.clientManagementService = clientManagementService;
	}

	@PostMapping
	public ResponseEntity<?> createMedicalRecord(@PathVariable String domain,
	                                             @ModelAttribute MedicalRecordForm medicalRecordForm) {
		clientManagementService.saveMedicalRecord(domain, medicalRecordForm);
		Map<String, Object> result = new HashMap<>();
		result.put("success", "Create medical record successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(result);

	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<?> updateMember(@PathVariable String id,
	                                      @PathVariable String domain,
	                                      @ModelAttribute MedicalRecordForm medicalRecordForm) {
		long idValue = Long.parseLong(id);
		clientManagementService.updateMedicalRecord(idValue, medicalRecordForm);
		Map<String, Object> result = new HashMap<>();
		result.put("success", "Update service info successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteMedicalRecord(@PathVariable String id, @PathVariable String domain) {
		long idValue = Long.parseLong(id);
		clientManagementService.deleteMedicalRecordById(idValue);
		return ResponseEntity.ok("Delete serviceItem id: " + id);
	}
}

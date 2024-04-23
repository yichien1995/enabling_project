package tw.appworks.school.yichien.enabling.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.appworks.school.yichien.enabling.dto.form.NewInstitutionForm;
import tw.appworks.school.yichien.enabling.service.InstitutionService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/institution")
public class InstitutionController {

	private final InstitutionService institutionService;

	public InstitutionController(InstitutionService institutionService) {
		this.institutionService = institutionService;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createNewInstitution(@ModelAttribute NewInstitutionForm form) {
		Map<String, Object> domainErrorMsg = institutionService.domainErrorMsg(form.getInstitutionDomain());
		if (domainErrorMsg != null) {
			return ResponseEntity.badRequest().body(domainErrorMsg);
		}
		// user id 從 session 中取得
		institutionService.createNewInstitution(1L, form);
		Map<String, Object> result = new HashMap<>();
		result.put("success", "Create institution successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}

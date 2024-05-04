package tw.appworks.school.yichien.enabling.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.TeamMemberForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.service.TeamManagementService;
import tw.appworks.school.yichien.enabling.service.webpage.HomepageService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/admin/{domain}/management")
public class TeamManagementApiController {

	private final TeamManagementService teamManagementService;

	private final HomepageService homepageService;

	public TeamManagementApiController(TeamManagementService teamManagementService, HomepageService homepageService) {
		this.teamManagementService = teamManagementService;
		this.homepageService = homepageService;
	}

	@PostMapping("/team")
	public ResponseEntity<?> newTeamMember(@PathVariable String domain,
	                                       @ModelAttribute TeamMemberForm teamMemberForm) {
		Map<String, Object> errorMsg = teamManagementService.saveInstitutionUser(teamMemberForm, domain);
		if (errorMsg != null) {
			return ResponseEntity.badRequest().body(errorMsg);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("success", "Create Member successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@DeleteMapping("/team/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteEvaluation(
			@PathVariable String id, @PathVariable String domain) {
		long idValue = Long.parseLong(id);
		teamManagementService.deleteInstitutionUserById(idValue);
		return ResponseEntity.ok("Delete institution_user id: " + id);
	}

	@PostMapping("/account")
	public ResponseEntity<?> updateInstitution(@PathVariable String domain, @ModelAttribute Institution institution) {
		homepageService.updateInstitution(domain, institution);
		Map<String, Object> result = new HashMap<>();
		result.put("success", "update institution info successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}

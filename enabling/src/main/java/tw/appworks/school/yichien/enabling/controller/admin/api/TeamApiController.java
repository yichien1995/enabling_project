package tw.appworks.school.yichien.enabling.controller.admin.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.TeamMemberForm;
import tw.appworks.school.yichien.enabling.service.TeamManagementService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/admin/{domain}/team")
public class TeamApiController {

    private final TeamManagementService teamManagementService;

    public TeamApiController(TeamManagementService teamManagementService) {
        this.teamManagementService = teamManagementService;
    }

    @PostMapping("/member")
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

    @DeleteMapping("/member/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteTeamMember(
            @PathVariable String id, @PathVariable String domain) {
        long idValue = Long.parseLong(id);
        teamManagementService.deleteInstitutionUserById(idValue);
        return ResponseEntity.ok("Delete institution_user id: " + id);
    }
}

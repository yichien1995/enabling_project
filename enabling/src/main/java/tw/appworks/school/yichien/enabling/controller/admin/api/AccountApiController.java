package tw.appworks.school.yichien.enabling.controller.admin.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.service.webpage.HomepageService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/admin/{domain}/account")
public class AccountApiController {

    private final HomepageService homepageService;

    public AccountApiController(HomepageService homepageService) {
        this.homepageService = homepageService;
    }

    @PostMapping
    public ResponseEntity<?> updateInstitution(@PathVariable String domain, @ModelAttribute Institution institution) {
        homepageService.updateInstitution(domain, institution);
        Map<String, Object> result = new HashMap<>();
        result.put("success", "update institution info successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

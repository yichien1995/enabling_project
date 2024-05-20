package tw.appworks.school.yichien.enabling.controller.admin.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.service.webpage.HomepageService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/admin/{domain}/webpage/homepage")
public class HomepageApiController {

    private final HomepageService homepageService;

    public HomepageApiController(HomepageService homepageService) {
        this.homepageService = homepageService;
    }

    @PostMapping(value = "/preview", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> savePreviewData(@PathVariable String domain,
                                             @ModelAttribute HomepageForm homepageForm) {
        homepageService.saveHomepageDraft(domain, homepageForm);
        Map<String, Object> result = new HashMap<>();
        result.put("success", "Save preview data successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateHomepageData(@PathVariable String domain,
                                                @ModelAttribute HomepageForm homepageForm) {
        homepageService.saveHomepage(domain, homepageForm);
        Map<String, Object> result = new HashMap<>();
        result.put("success", "Update homepage data successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

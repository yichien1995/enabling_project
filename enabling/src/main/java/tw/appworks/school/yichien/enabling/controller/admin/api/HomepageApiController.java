package tw.appworks.school.yichien.enabling.controller.admin.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.response.SuccessResponse;
import tw.appworks.school.yichien.enabling.service.webpage.HomepageService;

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
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Save homepage preview data successfully."));
    }

    @PostMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateHomepageData(@PathVariable String domain,
                                                @ModelAttribute HomepageForm homepageForm) {
        homepageService.saveHomepage(domain, homepageForm);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Update homepage data successfully."));
    }
}

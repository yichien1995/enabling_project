package tw.appworks.school.yichien.enabling.controller.webpage.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.ReserveEvaluationForm;
import tw.appworks.school.yichien.enabling.service.webpage.EvaluationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/1.0/{domain}")
public class WebpageApiController {

    private final EvaluationService evaluationService;

    public WebpageApiController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping("/evaluation")
    public ResponseEntity<?> reserveEvaluation(@PathVariable String domain,
                                               @ModelAttribute ReserveEvaluationForm reserveEvaluationForm) {
        evaluationService.reserveEvaluation(reserveEvaluationForm);
        Map<String, Object> result = new HashMap<>();
        result.put("success", "Reserve evaluation successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

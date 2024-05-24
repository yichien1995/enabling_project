package tw.appworks.school.yichien.enabling.controller.admin.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.NewEvaluationForm;
import tw.appworks.school.yichien.enabling.response.SuccessResponse;
import tw.appworks.school.yichien.enabling.service.webpage.EvaluationService;

@RestController
@RequestMapping("/api/1.0/admin/{domain}/webpage/evaluation")
public class EvaluationApiController {

    private final EvaluationService evaluationService;

    public EvaluationApiController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping
    public ResponseEntity<?> createNewEvaluation(@PathVariable String domain,
                                                 @ModelAttribute NewEvaluationForm newEvaluationForm) {
        evaluationService.saveNewEvaluation(domain, newEvaluationForm);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Create new evaluation successfully."));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteEvaluation(
            @PathVariable String id, @PathVariable String domain) {
        long idValue = Long.parseLong(id);
        evaluationService.deleteEvaluation(idValue);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Delete evaluation."));
    }
}

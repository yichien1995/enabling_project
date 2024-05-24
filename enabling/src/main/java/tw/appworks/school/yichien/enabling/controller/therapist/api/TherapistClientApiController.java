package tw.appworks.school.yichien.enabling.controller.therapist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.ClientReportForm;
import tw.appworks.school.yichien.enabling.response.ErrorResponse;
import tw.appworks.school.yichien.enabling.response.SuccessResponse;
import tw.appworks.school.yichien.enabling.service.ClientService;
import tw.appworks.school.yichien.enabling.service.impl.SessionServiceImpl;

import java.util.Map;

@RestController
@RequestMapping("api/1.0/therapist/{domain}/client")
public class TherapistClientApiController {

    public static final Logger logger = LoggerFactory.getLogger(TherapistClientApiController.class);
    @Autowired
    private final SessionServiceImpl sessionService;
    private final ClientService clientService;


    public TherapistClientApiController(SessionServiceImpl sessionService, ClientService clientService) {
        this.sessionService = sessionService;
        this.clientService = clientService;
    }

    @PostMapping("/list")
    public ResponseEntity<?> addIntervention(@PathVariable String domain,
                                             @RequestBody Map<String, Object> data,
                                             @CookieValue(value = "enabling", required = false) String sessionID)
            throws JsonProcessingException {
        Long medicalRecordId = ((Integer) data.get("medical_record_id")).longValue();
        Long institutionUserId = sessionService.getInstitutionUserIdFromSession(sessionID, domain);
        if (institutionUserId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Find no institution user id"));
        }
        clientService.saveIntervention(medicalRecordId, institutionUserId);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Add intervention successfully."));
    }

    @DeleteMapping("/list/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteIntervention(@PathVariable String id, @PathVariable String domain) {
        long idValue = Long.parseLong(id);
        clientService.deleteInterventionById(idValue);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Delete intervention."));
    }

    @PostMapping("/report")
    public ResponseEntity<?> createClientReport(@PathVariable String domain,
                                                @ModelAttribute ClientReportForm clientReportForm,
                                                @CookieValue(value = "enabling", required = false) String sessionID)
            throws JsonProcessingException {
        Long institutionUserId = sessionService.getInstitutionUserIdFromSession(sessionID, domain);
        if (institutionUserId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Find no institution user id"));
        }
        clientService.saveClientReport(institutionUserId, clientReportForm);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Create client report successfully."));
    }

    @PatchMapping(path = "/report/{id}")
    public ResponseEntity<?> updateClientReport(@PathVariable String id,
                                                @PathVariable String domain,
                                                @ModelAttribute ClientReportForm clientReportForm,
                                                @CookieValue(value = "enabling", required = false) String sessionID)
            throws JsonProcessingException {

        Long institutionUserId = sessionService.getInstitutionUserIdFromSession(sessionID, domain);
        if (institutionUserId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Find no institution user id"));
        }
        long idValue = Long.parseLong(id);
        clientService.updateClientReportById(idValue, clientReportForm);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Update client report successfully."));
    }

    @DeleteMapping("/report/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteClientReport(@PathVariable String id, @PathVariable String domain) {
        long idValue = Long.parseLong(id);
        clientService.deleteClientReportById(idValue);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Delete client report."));
    }

}

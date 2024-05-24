package tw.appworks.school.yichien.enabling.controller.admin.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.MedicalRecordForm;
import tw.appworks.school.yichien.enabling.response.SuccessResponse;
import tw.appworks.school.yichien.enabling.service.ClientManagementService;

@RestController
@RequestMapping("api/1.0/admin/{domain}/client")
public class AdminClientApiController {

    private final ClientManagementService clientManagementService;

    public AdminClientApiController(ClientManagementService clientManagementService) {
        this.clientManagementService = clientManagementService;
    }

    @PostMapping
    public ResponseEntity<?> createMedicalRecord(@PathVariable String domain,
                                                 @ModelAttribute MedicalRecordForm medicalRecordForm) {
        clientManagementService.saveMedicalRecord(domain, medicalRecordForm);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Create medical record successfully."));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> updateMedicalRecord(@PathVariable String id,
                                                 @PathVariable String domain,
                                                 @ModelAttribute MedicalRecordForm medicalRecordForm) {
        long idValue = Long.parseLong(id);
        clientManagementService.updateMedicalRecord(idValue, medicalRecordForm);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Update medical record successfully."));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteMedicalRecord(@PathVariable String id, @PathVariable String domain) {
        long idValue = Long.parseLong(id);
        clientManagementService.deleteMedicalRecordById(idValue);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Delete medical record."));
    }
}

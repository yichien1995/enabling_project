package tw.appworks.school.yichien.enabling.controller.admin.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.ServicesForm;
import tw.appworks.school.yichien.enabling.response.SuccessResponse;
import tw.appworks.school.yichien.enabling.service.webpage.ServiceItemService;

@RestController
@RequestMapping("api/1.0/admin/{domain}/webpage/service")
public class ServicesApiController {

    private final ServiceItemService serviceItemService;

    public ServicesApiController(ServiceItemService serviceItemService) {
        this.serviceItemService = serviceItemService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createServiceItem(@PathVariable String domain,
                                               @ModelAttribute ServicesForm servicesForm) {
        serviceItemService.saveService(domain, servicesForm);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Create service item successfully."));
    }

    @PatchMapping(path = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateServiceItem(@PathVariable String id, @PathVariable String domain,
                                               @ModelAttribute ServicesForm servicesForm) {
        long idValue = Long.parseLong(id);
        serviceItemService.updateService(domain, idValue, servicesForm);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Update service item info successfully."));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteServiceItem(@PathVariable String id,
                                               @PathVariable String domain) {
        long idValue = Long.parseLong(id);
        serviceItemService.deleteServiceItemById(idValue);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Delete service item."));
    }
}

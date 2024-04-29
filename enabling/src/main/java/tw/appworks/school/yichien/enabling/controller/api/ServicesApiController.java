package tw.appworks.school.yichien.enabling.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.dto.form.ServicesForm;
import tw.appworks.school.yichien.enabling.service.webpage.ServiceItemService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/admin/{domain}/setting/service")
public class ServicesApiController {

	private final ServiceItemService serviceItemService;

	public ServicesApiController(ServiceItemService serviceItemService) {
		this.serviceItemService = serviceItemService;
	}

	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> createServices(@PathVariable String domain,
	                                        @ModelAttribute ServicesForm servicesForm) {
		serviceItemService.saveService(domain, servicesForm);
		Map<String, Object> result = new HashMap<>();
		result.put("success", "Create service item successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<?> deleteServiceItem(@PathVariable String id, @PathVariable String domain) {
		long idValue = Long.parseLong(id);
		serviceItemService.deleteServiceItemById(idValue);
		return ResponseEntity.ok("Delete serviceItem id: " + id);
	}
}
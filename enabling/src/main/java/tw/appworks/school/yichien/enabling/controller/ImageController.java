package tw.appworks.school.yichien.enabling.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

	@GetMapping("/uploads/{domain}/{filename:.+}")
	public ResponseEntity<Resource> getCampaignsImage(@PathVariable String domain,
	                                                  @PathVariable String filename) throws IOException {
		return configImage("/uploads/" + domain + "/", filename);
	}

	private ResponseEntity<Resource> configImage(String dir, String filename) throws IOException {
		// get the file path of the image
		String uploadDir = System.getProperty("user.dir") + dir;
		Path filePath = Paths.get(uploadDir + filename);

		// Get the MIME type of the image file
		String contentType = Files.probeContentType(filePath);

		// Return image file
		Resource resource = new UrlResource(filePath.toUri());

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.body(resource);
	}
}

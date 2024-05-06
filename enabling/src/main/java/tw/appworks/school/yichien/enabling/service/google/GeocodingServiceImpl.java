package tw.appworks.school.yichien.enabling.service.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

@Service
public class GeocodingServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(GeocodingServiceImpl.class);
	private final String API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";

	@Value("${google.api.key}")
	private String API_KEY;

	public void getGeocodingResponse(String address, Model model) {
		RestTemplate restTemplate = new RestTemplate();

		String targetUrl = API_URL + address + "&key=" + API_KEY;
		ResponseEntity<Map> response = restTemplate.getForEntity(targetUrl, Map.class);


		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && !response.getBody().isEmpty()) {
			Map<String, Object> data = response.getBody();
			ArrayList<Object> results = (ArrayList<Object>) data.get("results");
			Map<String, Object> resultsData = (Map<String, Object>) results.get(0);
			Map<String, Object> geometry = (Map<String, Object>) resultsData.get("geometry");
			Map<String, Object> location = (Map<String, Object>) geometry.get("location");


			ObjectMapper objectMapper = new ObjectMapper();
			LocationObject locationObject = objectMapper.convertValue(location, LocationObject.class);

			logger.info("fetch data: {}", locationObject);
			model.addAttribute("location", locationObject);
		}
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	private static class LocationObject {
		private Double lat;
		private Double lng;
	}

}

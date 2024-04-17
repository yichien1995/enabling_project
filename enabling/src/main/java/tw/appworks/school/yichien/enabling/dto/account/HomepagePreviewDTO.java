package tw.appworks.school.yichien.enabling.dto.account;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class HomepagePreviewDTO {
	private MultipartFile logo;
	private MultipartFile mainImage;
	private String imageDescription;
	private String institutionIntro;
	private String backgroundColor;
	private String headingColor;

}

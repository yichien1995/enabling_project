package tw.appworks.school.yichien.enabling.dto.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ArticleForm {
	private MultipartFile cover;
	private String title;
	private String content;
}

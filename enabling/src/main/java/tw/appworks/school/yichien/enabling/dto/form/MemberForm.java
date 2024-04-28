package tw.appworks.school.yichien.enabling.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberForm {

	private String name;

	private MultipartFile photo;

	private String title;

	private String qualification;

	private String education;
}

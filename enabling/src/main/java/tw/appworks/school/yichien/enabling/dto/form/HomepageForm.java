package tw.appworks.school.yichien.enabling.dto.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class HomepageForm {
    private MultipartFile logo;
    private MultipartFile mainImage;
    private String imageDescription;
    private String institutionIntro;
    private Integer color;
}

package tw.appworks.school.yichien.enabling.dto.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ServicesForm {
    private MultipartFile image;
    private String title;
    private String price;
}

package tw.appworks.school.yichien.enabling.dto.webpage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HomepageDto {

	private Integer themeColorId;

	private String mainImage;

	private String imageDescription;

	private String logo;

	private String institutionIntro;

	private String institutionDomain;
}

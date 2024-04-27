package tw.appworks.school.yichien.enabling.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyInstitutionDto {

	private String role;
	private String institutionDomain;
	private String institutionName;
}

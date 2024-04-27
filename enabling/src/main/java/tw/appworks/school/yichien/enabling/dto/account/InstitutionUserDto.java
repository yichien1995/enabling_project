package tw.appworks.school.yichien.enabling.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionUserDto {
	private long InstitutionUserId;
	private String institutionDomain;
	private long userId;
	private int roleId;
	private int employeeId;
}

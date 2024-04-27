package tw.appworks.school.yichien.enabling.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberForm {
	private Integer employeeId;
	private String email;
	private Integer roleId;
}

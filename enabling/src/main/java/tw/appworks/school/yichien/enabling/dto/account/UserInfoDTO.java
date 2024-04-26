package tw.appworks.school.yichien.enabling.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

	private Long userId;
	private String userName;
	private String userEmail;
}

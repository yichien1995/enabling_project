package tw.appworks.school.yichien.enabling.dto.account;

import lombok.Data;

@Data
public class MemberDTO {

	private Long institutionUserId;
	private String name;

	public MemberDTO(Long institutionUserId, String userName) {
		this.institutionUserId = institutionUserId;
		this.name = userName;
	}
}

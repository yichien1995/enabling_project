package tw.appworks.school.yichien.enabling.dto.account;

import lombok.Data;

@Data
public class MemberDto {

    private Long institutionUserId;
    private String name;

    public MemberDto(Long institutionUserId, String userName) {
        this.institutionUserId = institutionUserId;
        this.name = userName;
    }
}

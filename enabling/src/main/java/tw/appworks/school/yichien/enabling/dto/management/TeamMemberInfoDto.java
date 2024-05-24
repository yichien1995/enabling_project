package tw.appworks.school.yichien.enabling.dto.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberInfoDto {
    private Long institutionUserId;
    private Integer employeeId;
    private String userName;
    private String email;
    private String role;
}

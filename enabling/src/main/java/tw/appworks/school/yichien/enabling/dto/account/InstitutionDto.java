package tw.appworks.school.yichien.enabling.dto.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstitutionDto {

    private String domainName;

    private String institutionName;

    private String address;

    private String tel;

    private String businessHour;

    private Integer webpageAvailable;
}

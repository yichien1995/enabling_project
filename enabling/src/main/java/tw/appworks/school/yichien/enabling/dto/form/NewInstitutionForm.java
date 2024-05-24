package tw.appworks.school.yichien.enabling.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewInstitutionForm {

    private String institutionDomain;
    private String institutionName;
    private String tel;
    private String address;
    private String businessHour;
}

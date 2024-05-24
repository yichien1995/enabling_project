package tw.appworks.school.yichien.enabling.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordForm {

    private Integer medicalRecordNumber;

    private String nationalIdNumber;

    private String name;

    private LocalDate birthday;

    private String tel;

    private String email;
}

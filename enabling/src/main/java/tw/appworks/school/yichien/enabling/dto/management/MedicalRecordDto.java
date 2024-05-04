package tw.appworks.school.yichien.enabling.dto.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDto {

	private Long medicalRecordId;

	private Integer medicalRecordNumber;

	private String name;
}

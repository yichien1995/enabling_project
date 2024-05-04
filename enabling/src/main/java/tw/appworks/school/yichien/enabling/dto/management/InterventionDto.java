package tw.appworks.school.yichien.enabling.dto.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterventionDto {

	private Long interventionId;

	private Integer medicalRecordNumber;

	private String name;

	private LocalDate birthday;

	private String tel;

	private String email;
}

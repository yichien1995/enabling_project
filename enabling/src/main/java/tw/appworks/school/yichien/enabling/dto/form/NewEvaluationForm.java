package tw.appworks.school.yichien.enabling.dto.form;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class NewEvaluationForm {

	private LocalDate evaluationDate;

	private LocalTime evaluationTime;

	private String institutionUserId;
}

package tw.appworks.school.yichien.enabling.dto.form;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReserveEvaluationForm {

    private long evaluationId;

    private String clientName;

    private LocalDate birthday;

    private String tel;

    private String email;
}

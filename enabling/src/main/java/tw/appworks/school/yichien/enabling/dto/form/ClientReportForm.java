package tw.appworks.school.yichien.enabling.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientReportForm {

    private LocalDate date;
    private Integer totalAttendance;
}

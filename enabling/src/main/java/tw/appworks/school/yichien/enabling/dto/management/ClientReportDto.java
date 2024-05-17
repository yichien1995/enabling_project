package tw.appworks.school.yichien.enabling.dto.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientReportDto {
    private Long id;
    private LocalDate date;
    private Integer totalAttendance;
}

package tw.appworks.school.yichien.enabling.dto.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientReportChartData {
	private Long institutionUserId;
	private String name;
	private ArrayList<LocalDate> xData;
	private ArrayList<Integer> yData;
}

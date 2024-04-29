package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.management.ClientReportChartData;
import tw.appworks.school.yichien.enabling.dto.management.ClientReportDto;
import tw.appworks.school.yichien.enabling.dto.management.TeamMemberInfoDto;
import tw.appworks.school.yichien.enabling.repository.projection.ProjectionRepo;
import tw.appworks.school.yichien.enabling.service.ReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReportServiceImpl implements ReportService {

	private final ProjectionRepo projectionRepo;

	public ReportServiceImpl(ProjectionRepo projectionRepo) {
		this.projectionRepo = projectionRepo;
	}

	@Override
	public void renderReportPage(String domain, Model model) {
		ArrayList<ClientReportChartData> allChartData = getAllChartData(domain);
		model.addAttribute("allChartData", allChartData);
	}

	private ArrayList<ClientReportChartData> getAllChartData(String domain) {
		List<TeamMemberInfoDto> therapistList = projectionRepo.getTherapistInfoDTO(domain);
		ArrayList<ClientReportChartData> result = new ArrayList<>();
		for (int i = 0; i < therapistList.size(); i++) {
			ClientReportChartData clientReportChartData = new ClientReportChartData();
			Long id = therapistList.get(i).getInstitutionUserId();
			String name = therapistList.get(i).getUserName();

			clientReportChartData.setInstitutionUserId(id);
			clientReportChartData.setName(name);

			//set chart x,y data
			List<ClientReportDto> allData = projectionRepo.getClientReportDto(id);
			ArrayList<LocalDate> x = new ArrayList<>();
			ArrayList<Integer> y = new ArrayList<>();

			for (ClientReportDto data : allData) {
				x.add(data.getDate());
				y.add(data.getTotalAttendance());
			}

			clientReportChartData.setXData(x);
			clientReportChartData.setYData(y);

			result.add(clientReportChartData);
		}
		return result;
	}
}

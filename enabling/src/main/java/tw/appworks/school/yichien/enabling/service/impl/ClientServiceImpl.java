package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
import tw.appworks.school.yichien.enabling.dto.form.ClientReportForm;
import tw.appworks.school.yichien.enabling.model.account.InstitutionUser;
import tw.appworks.school.yichien.enabling.model.clinial.ClientReport;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionUserRepository;
import tw.appworks.school.yichien.enabling.repository.clinial.ClientReportRepository;
import tw.appworks.school.yichien.enabling.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	private final InstitutionUserRepository institutionUserRepository;

	private final ClientReportRepository clientReportRepository;

	public ClientServiceImpl(InstitutionUserRepository institutionUserRepository, ClientReportRepository clientReportRepository) {
		this.institutionUserRepository = institutionUserRepository;
		this.clientReportRepository = clientReportRepository;
	}

	@Override
	public void saveClientReport(Long institutionUserId, ClientReportForm form) {
		ClientReport clientReport = new ClientReport();
		InstitutionUser institutionUser = institutionUserRepository.findInstitutionUserById(institutionUserId);

		clientReport.setDate(form.getDate());
		clientReport.setTotalAttendance(form.getTotalAttendance());
		clientReport.setInstitutionUserId(institutionUser);

		clientReportRepository.save(clientReport);
	}
}

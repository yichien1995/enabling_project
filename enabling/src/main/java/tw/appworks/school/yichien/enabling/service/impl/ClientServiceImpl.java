package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.ClientReportForm;
import tw.appworks.school.yichien.enabling.dto.management.InterventionDto;
import tw.appworks.school.yichien.enabling.dto.management.MedicalRecordDto;
import tw.appworks.school.yichien.enabling.model.account.InstitutionUser;
import tw.appworks.school.yichien.enabling.model.clinial.ClientReport;
import tw.appworks.school.yichien.enabling.model.clinial.Intervention;
import tw.appworks.school.yichien.enabling.model.clinial.MedicalRecord;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionUserRepository;
import tw.appworks.school.yichien.enabling.repository.clinial.ClientReportRepository;
import tw.appworks.school.yichien.enabling.repository.clinial.InterventionRepository;
import tw.appworks.school.yichien.enabling.repository.clinial.MedicalRecordRepository;
import tw.appworks.school.yichien.enabling.repository.projection.ProjectionRepo;
import tw.appworks.school.yichien.enabling.service.ClientService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

	private final InstitutionUserRepository institutionUserRepository;

	private final ClientReportRepository clientReportRepository;

	private final MedicalRecordRepository medicalRecordRepository;

	private final InterventionRepository interventionRepository;

	private final ProjectionRepo projectionRepo;

	public ClientServiceImpl(InstitutionUserRepository institutionUserRepository,
	                         ClientReportRepository clientReportRepository,
	                         MedicalRecordRepository medicalRecordRepository,
	                         InterventionRepository interventionRepository,
	                         ProjectionRepo projectionRepo) {
		this.institutionUserRepository = institutionUserRepository;
		this.clientReportRepository = clientReportRepository;
		this.medicalRecordRepository = medicalRecordRepository;
		this.interventionRepository = interventionRepository;
		this.projectionRepo = projectionRepo;
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

	@Override
	public void saveIntervention(Long medicalRecordId, Long institutionUserId) {
		Intervention intervention = new Intervention();
		MedicalRecord medicalRecord = medicalRecordRepository.findMedicalRecordById(medicalRecordId);
		InstitutionUser institutionUser = institutionUserRepository.findInstitutionUserById(institutionUserId);

		intervention.setMedicalRecordId(medicalRecord);
		intervention.setInstitutionUserId(institutionUser);

		interventionRepository.save(intervention);
	}

	@Override
	public void renderClientListPage(Long institutionUserId, String domain, Model model) {
		List<MedicalRecordDto> medicalRecords = getMedicalRecordDto(domain);
		List<InterventionDto> interventions = projectionRepo.getInterventionDto(institutionUserId);
		model.addAttribute("medicalRecords", medicalRecords);
		model.addAttribute("interventionDto", interventions);
	}

	@Override
	@Transactional
	public void deleteInterventionById(Long id) {
		interventionRepository.deleteInterventionById(id);
	}

	private List<MedicalRecordDto> getMedicalRecordDto(String domain) {
		return projectionRepo.getMedicalRecordDto(domain);
	}
}

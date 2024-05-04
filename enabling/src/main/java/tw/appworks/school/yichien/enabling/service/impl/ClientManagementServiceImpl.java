package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.dto.form.MedicalRecordForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.clinial.MedicalRecord;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.repository.clinial.MedicalRecordRepository;
import tw.appworks.school.yichien.enabling.service.ClientManagementService;

import java.util.List;

@Service
public class ClientManagementServiceImpl implements ClientManagementService {

	private final MedicalRecordRepository medicalRecordRepository;

	private final InstitutionRepository institutionRepository;

	public ClientManagementServiceImpl(MedicalRecordRepository medicalRecordRepository, InstitutionRepository institutionRepository) {
		this.medicalRecordRepository = medicalRecordRepository;
		this.institutionRepository = institutionRepository;
	}

	@Override
	public void saveMedicalRecord(String domain, MedicalRecordForm form) {
		MedicalRecord medicalRecord = new MedicalRecord();
		Institution institution = institutionRepository.getInstitution(domain);

		medicalRecord.setMedicalRecordNumber(form.getMedicalRecordNumber());
		medicalRecord.setNationalIdNumber(form.getNationalIdNumber());
		medicalRecord.setName(form.getName());
		medicalRecord.setBirthday(form.getBirthday());
		medicalRecord.setTel(form.getTel());
		medicalRecord.setEmail(form.getEmail());
		medicalRecord.setInstitutionDomain(institution);

		medicalRecordRepository.save(medicalRecord);
	}

	@Override
	public void renderClientManagementPage(String domain, Model model) {
		List<MedicalRecord> medicalRecords = medicalRecordRepository.getAllMedicalRecordByDomain(domain);
		model.addAttribute("medicalRecords", medicalRecords);
	}

	@Override
	public void updateMedicalRecord(Long id, MedicalRecordForm form) {
		MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecordById(id);
		medicalRecord.setMedicalRecordNumber(form.getMedicalRecordNumber());
		medicalRecord.setNationalIdNumber(form.getNationalIdNumber());
		medicalRecord.setName(form.getName());
		medicalRecord.setBirthday(form.getBirthday());
		medicalRecord.setTel(form.getTel());
		medicalRecord.setEmail(form.getEmail());

		medicalRecordRepository.save(medicalRecord);
	}

	@Override
	@Transactional
	public void deleteMedicalRecordById(Long id) {
		medicalRecordRepository.deleteMedicalRecordById(id);
	}
}

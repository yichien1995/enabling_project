package tw.appworks.school.yichien.enabling.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import tw.appworks.school.yichien.enabling.EnablingApplication;
import tw.appworks.school.yichien.enabling.dto.form.MedicalRecordForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;
import tw.appworks.school.yichien.enabling.model.clinial.MedicalRecord;
import tw.appworks.school.yichien.enabling.repository.account.InstitutionRepository;
import tw.appworks.school.yichien.enabling.repository.clinial.MedicalRecordRepository;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EnablingApplication.class, properties = {
        "spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false",
        "spring.jpa.hibernate.ddl-auto=none"
})
public class ClientManagementServiceTest {

    @Autowired
    private ClientManagementService clientManagementService;

    @MockBean
    private MedicalRecordRepository medicalRecordRepository;

    @MockBean
    private InstitutionRepository institutionRepository;

    private MedicalRecordForm form1;

    private List<MedicalRecord> fakeMedicalRecords;
    private MedicalRecord mr1, mr2, mr3;

    private Institution i1;

    private Model model;

    @Before
    public void init() {
        i1 = new Institution();
        i1.setDomainName("fakeDomain");

        form1 = new MedicalRecordForm();
        form1.setMedicalRecordNumber(123456);
        form1.setNationalIdNumber("A123456789");
        form1.setName("fakeName-1");
        form1.setBirthday(LocalDate.of(2023, 1, 1));
        form1.setTel("02-12345678-1");
        form1.setEmail("fake@email-1");

        mr1 = new MedicalRecord();
        mr1.setMedicalRecordNumber(123456);
        mr1.setNationalIdNumber("A123456789");
        mr1.setName("fakeName-1");
        mr1.setBirthday(LocalDate.of(2023, 1, 1));
        mr1.setTel("02-12345678-1");
        mr1.setEmail("fake@email-1");
        mr1.setInstitutionDomain(i1);

        mr2 = new MedicalRecord();
        mr2.setMedicalRecordNumber(223456);
        mr2.setNationalIdNumber("B223456789");
        mr2.setName("fakeName-2");
        mr2.setBirthday(LocalDate.of(2023, 2, 2));
        mr2.setTel("02-12345678-2");
        mr2.setEmail("fake@email-2");
        mr2.setInstitutionDomain(i1);

        fakeMedicalRecords = List.of(mr1, mr2);

        model = mock(Model.class);

        // existing medical record
        mr3 = new MedicalRecord();
        mr3.setId(332456L);
        mr3.setMedicalRecordNumber(323456);
        mr3.setNationalIdNumber("C323456789");
        mr3.setName("fakeName-3");
        mr3.setBirthday(LocalDate.of(2023, 3, 3));
        mr3.setTel("02-12345678-3");
        mr3.setEmail("fake@email-3");
        mr3.setInstitutionDomain(i1);
    }

    @Test
    public void save_medical_record_success() {
        when(institutionRepository.getInstitution(any())).thenReturn(i1);

        // Call the method under test
        clientManagementService.saveMedicalRecord("fakeDomain", form1);

        // Verify that the institutionRepository.getInstitution method was called
        verify(institutionRepository, times(1)).getInstitution("fakeDomain");

        // Verify that the medicalRecordRepository.save method was called with the correct MedicalRecord
        verify(medicalRecordRepository, times(1)).save(argThat(record ->
                record.getMedicalRecordNumber().equals(form1.getMedicalRecordNumber()) &&
                        record.getNationalIdNumber().equals(form1.getNationalIdNumber()) &&
                        record.getName().equals(form1.getName()) &&
                        record.getBirthday().equals(form1.getBirthday()) &&
                        record.getTel().equals(form1.getTel()) &&
                        record.getEmail().equals(form1.getEmail()) &&
                        record.getInstitutionDomain().equals(i1)
        ));
    }

    @Test
    public void render_client_management_page_success() {
        String domain = "fakeDomain";

        when(medicalRecordRepository.getAllMedicalRecordByDomain(domain)).thenReturn(fakeMedicalRecords);

        clientManagementService.renderClientManagementPage(domain, model);

        // Verify that the medicalRecordRepository.getAllMedicalRecordByDomain method was called
        verify(medicalRecordRepository, times(1)).getAllMedicalRecordByDomain(domain);

        // Verify that the model.addAttribute method was called with the correct attributes
        verify(model, times(1)).addAttribute("medicalRecords", fakeMedicalRecords);
    }

    @Test
    public void update_medical_record_success() {
        Long id = mr3.getId();

        when(medicalRecordRepository.findMedicalRecordById(id)).thenReturn(mr3);

        // update mr3 with data of form1
        clientManagementService.updateMedicalRecord(id, form1);

        verify(medicalRecordRepository, times(1)).findMedicalRecordById(id);

        verify(medicalRecordRepository, times(1)).save(argThat(record ->
                record.getId().equals(mr3.getId()) &&
                        record.getMedicalRecordNumber().equals(form1.getMedicalRecordNumber()) &&
                        record.getNationalIdNumber().equals(form1.getNationalIdNumber()) &&
                        record.getName().equals(form1.getName()) &&
                        record.getBirthday().equals(form1.getBirthday()) &&
                        record.getTel().equals(form1.getTel()) &&
                        record.getEmail().equals(form1.getEmail())
        ));
    }

    @Test
    public void delete_medical_record_by_id_success() {
        Long medicalRecordId = 1L;

        // Call the method under test
        clientManagementService.deleteMedicalRecordById(medicalRecordId);

        // Verify that the medicalRecordRepository.deleteMedicalRecordById method was called with the correct ID
        verify(medicalRecordRepository, times(1)).deleteMedicalRecordById(medicalRecordId);
    }
}

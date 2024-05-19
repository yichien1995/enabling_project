package tw.appworks.school.yichien.enabling.model.clinial;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.yichien.enabling.dto.form.MedicalRecordForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;

import java.time.LocalDate;

@Entity
@Table(name = "medical_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medical_record_number")
    private Integer medicalRecordNumber;

    @Column(name = "national_id_number", columnDefinition = "char(10)")
    private String nationalIdNumber;

    @Column(name = "name", columnDefinition = "varchar(50)")
    private String name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    @JoinColumn(name = "institution_domain", referencedColumnName = "domain_name", nullable = false)
    @ManyToOne
    private Institution institutionDomain;

    public static MedicalRecord convertNewForm(MedicalRecordForm form, Institution institution) {
        MedicalRecord m = new MedicalRecord();
        m.setInstitutionDomain(institution);
        return convertUpdateForm(form, m);
    }

    public static MedicalRecord convertUpdateForm(MedicalRecordForm form, MedicalRecord m) {
        m.setMedicalRecordNumber(form.getMedicalRecordNumber());
        m.setNationalIdNumber(form.getNationalIdNumber());
        m.setName(form.getName());
        m.setBirthday(form.getBirthday());
        m.setTel(form.getTel());
        m.setEmail(form.getEmail());
        return m;
    }
}

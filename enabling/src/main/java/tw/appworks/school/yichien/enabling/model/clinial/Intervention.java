package tw.appworks.school.yichien.enabling.model.clinial;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tw.appworks.school.yichien.enabling.model.account.InstitutionUser;

@Entity
@Table(name = "intervention")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "medical_record_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private MedicalRecord medicalRecordId;

    @JoinColumn(name = "institution_user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InstitutionUser institutionUserId;
}

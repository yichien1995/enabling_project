package tw.appworks.school.yichien.enabling.model.clinial;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.yichien.enabling.dto.form.NewEvaluationForm;
import tw.appworks.school.yichien.enabling.dto.form.ReserveEvaluationForm;
import tw.appworks.school.yichien.enabling.model.account.InstitutionUser;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "evaluation")
@Data
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "evaluation_date")
    private LocalDate evaluationDate;

    @Column(name = "evaluation_time")
    private LocalTime evaluationTime;

    @JoinColumn(name = "institution_user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private InstitutionUser institutionUserId;

    @Column(name = "reserved", columnDefinition = "tinyint default 0")
    private Integer reserved;

    @Column(name = "client_name", columnDefinition = "varchar(50)")
    private String clientName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    public static Evaluation convertNewEvaluationForm(NewEvaluationForm form,
                                                      InstitutionUser institutionUser) {
        Evaluation e = new Evaluation();
        e.setEvaluationDate(form.getEvaluationDate());
        e.setEvaluationTime(form.getEvaluationTime());
        e.setInstitutionUserId(institutionUser);
        e.setReserved(0);
        return e;
    }

    public static Evaluation convertReserveEvaluationForm(ReserveEvaluationForm form, Evaluation e) {
        e.setClientName(form.getClientName());
        e.setBirthday(form.getBirthday());
        e.setTel(form.getTel());
        e.setEmail(form.getEmail());
        e.setReserved(1);
        return e;
    }
}

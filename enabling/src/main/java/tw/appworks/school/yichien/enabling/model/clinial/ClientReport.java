package tw.appworks.school.yichien.enabling.model.clinial;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tw.appworks.school.yichien.enabling.dto.form.ClientReportForm;
import tw.appworks.school.yichien.enabling.model.account.InstitutionUser;

import java.time.LocalDate;

@Entity
@Table(name = "client_report")
@Data
@NoArgsConstructor
public class ClientReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_date")
    private LocalDate date;

    @Column(name = "total_attendance")
    private Integer totalAttendance;

    @JoinColumn(name = "institution_user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InstitutionUser institutionUserId;

    public static ClientReport convertNewForm(ClientReportForm form, InstitutionUser institutionUser) {
        ClientReport c = new ClientReport();
        c.setInstitutionUserId(institutionUser);
        return convertUpdateForm(form, c);
    }

    public static ClientReport convertUpdateForm(ClientReportForm form, ClientReport c) {
        c.setDate(form.getDate());
        c.setTotalAttendance(form.getTotalAttendance());
        return c;
    }
}
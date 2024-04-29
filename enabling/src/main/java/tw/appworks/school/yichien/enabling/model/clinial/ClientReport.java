package tw.appworks.school.yichien.enabling.model.clinial;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
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
	private InstitutionUser institutionUserId;
}
package tw.appworks.school.yichien.enabling.model.account;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "institution_user")
@Data
@NoArgsConstructor
public class InstitutionUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "institution_domain", referencedColumnName = "domain_name", nullable = false)
	@ManyToOne
	private Institution institutionDomain;

	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	@ManyToOne
	private Users userId;

	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
	@ManyToOne
	private Role roleId;

	@Column(name = "employee_id")
	private Integer employeeId;
}

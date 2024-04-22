package tw.appworks.school.yichien.enabling.model.account;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name", nullable = false)
	private String username;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	//	@Column(name = "password", columnDefinition = "BINARY(60)",nullable = false)
	@Column(name = "password", nullable = false)
	private String password;
}

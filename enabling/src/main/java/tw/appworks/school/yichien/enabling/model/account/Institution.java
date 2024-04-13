package tw.appworks.school.yichien.enabling.model.account;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "institution")
@Data
@NoArgsConstructor
public class Institution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "domain_name", nullable = false, unique = true)
	private String domainName;

	@Column(name = "institution_name")
	private String institutionName;

	@Column(name = "address")
	private String address;

	@Column(name = "tel")
	private String tel;

	@Column(name = "business_hour")
	private String businessHour;

	@Column(name = "webpage_available")
	private Integer webpageAvailable;

	public Institution(String domainName, String institutionName, String address, String tel, String businessHour, Integer webpageAvailable) {
		this.domainName = domainName;
		this.institutionName = institutionName;
		this.address = address;
		this.tel = tel;
		this.businessHour = businessHour;
		this.webpageAvailable = webpageAvailable;
	}
}

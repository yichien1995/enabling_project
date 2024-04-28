package tw.appworks.school.yichien.enabling.model.webpage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.yichien.enabling.model.account.Institution;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
public class ServiceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", columnDefinition = "varchar(50) default '服務項目'")
	private String title;

	@Column(name = "price", columnDefinition = "varchar(255) default '收費說明'")
	private String price;

	@Column(name = "image", columnDefinition = "varchar(255) default '圖片網址'")
	private String image;

	@JoinColumn(name = "institution_domain", referencedColumnName = "domain_name", nullable = false)
	@ManyToOne
	private Institution institutionDomain;
}

package tw.appworks.school.yichien.enabling.model.webpage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.yichien.enabling.model.account.Institution;

@Entity
@Table(name = "homepage")
@Data
@NoArgsConstructor
public class Homepage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JoinColumn(name = "theme_color_id", referencedColumnName = "id", nullable = false)
	@ManyToOne
	private ThemeColor themeColorId;

	@Column(name = "main_image")
	private String mainImage;

	@Column(name = "image_description")
	private String imageDescription;

	@Column(name = "logo")
	private String logo;

	@Column(name = "institution_intro")
	private String institutionIntro;

	@JoinColumn(name = "institution_domain", referencedColumnName = "domain_name", nullable = false)
	@OneToOne
	private Institution institutionDomain;

	public Homepage(ThemeColor themeColorId, String mainImage, String imageDescription, String logo, String institutionIntro, Institution institutionDomain) {
		this.themeColorId = themeColorId;
		this.mainImage = mainImage;
		this.imageDescription = imageDescription;
		this.logo = logo;
		this.institutionIntro = institutionIntro;
		this.institutionDomain = institutionDomain;
	}

}

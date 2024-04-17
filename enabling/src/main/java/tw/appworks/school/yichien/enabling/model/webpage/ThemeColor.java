package tw.appworks.school.yichien.enabling.model.webpage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theme_color")
@Data
@NoArgsConstructor
public class ThemeColor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "color")
	private String color;

	@Column(name = "background_color", length = 7)
	private String backgroundColor;

	@Column(name = "heading_color", length = 7)
	private String headingColor;

	public ThemeColor(String color) {
		this.color = color;
	}
}

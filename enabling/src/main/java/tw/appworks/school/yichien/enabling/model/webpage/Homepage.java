package tw.appworks.school.yichien.enabling.model.webpage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.yichien.enabling.dto.form.HomepageForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;

@Entity
@Table(name = "homepage")
@Data
@NoArgsConstructor
public class Homepage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "theme_color_id", referencedColumnName = "id", columnDefinition = "int default 1")
    @ManyToOne
    private ThemeColor themeColorId;

    @Column(name = "main_image", columnDefinition = "varchar(255) default '預設主要圖片網址'")
    private String mainImage;

    @Column(name = "image_description", columnDefinition = "varchar(100) default '輸入圖片敘述'")
    private String imageDescription;

    @Column(name = "logo", columnDefinition = "varchar(255) default '預設機構logo網址'")
    private String logo;

    @Column(name = "institution_intro", columnDefinition = "varchar(1000) default '輸入機構敘述'")
    private String institutionIntro;

    @JoinColumn(name = "institution_domain", referencedColumnName = "domain_name", nullable = false)
    @ManyToOne
    private Institution institutionDomain;

    @Column(name = "status", columnDefinition = "tinyint default 0")
    private Integer status;

    public static Homepage convertForm(HomepageForm form, Homepage h) {
        h.setImageDescription(form.getImageDescription());
        h.setInstitutionIntro(form.getInstitutionIntro());

        // set theme color
        ThemeColor color = new ThemeColor();
        color.setId(form.getColor());
        h.setThemeColorId(color);

        return h;
    }
}

package tw.appworks.school.yichien.enabling.model.webpage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.yichien.enabling.model.account.Institution;

@Entity
@Table(name = "article")
@Data
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cover", columnDefinition = "varchar(255) default '預設主要圖片網址'")
    private String cover;

    @Column(name = "title", columnDefinition = "varchar(50) default '輸入文章標題'")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "draft", columnDefinition = "tinyint", nullable = false)
    private int draft;

    @Column(name = "preview", columnDefinition = "tinyint", nullable = false)
    private int preview;

    @JoinColumn(name = "institution_domain", referencedColumnName = "domain_name", nullable = false)
    @ManyToOne
    private Institution institutionDomain;
}

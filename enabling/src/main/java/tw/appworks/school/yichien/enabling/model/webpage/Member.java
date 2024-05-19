package tw.appworks.school.yichien.enabling.model.webpage;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.yichien.enabling.dto.form.MemberForm;
import tw.appworks.school.yichien.enabling.model.account.Institution;

@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(50)")
    private String name;

    @Column(name = "photo", columnDefinition = "varchar(100)")
    private String photo;

    @Column(name = "title", columnDefinition = "varchar(500)")
    private String title;

    @Column(name = "qualification", columnDefinition = "varchar(1000)")
    private String qualification;

    @Column(name = "education", columnDefinition = "varchar(500)")
    private String education;

    @JoinColumn(name = "institution_domain", referencedColumnName = "domain_name", nullable = false)
    @ManyToOne
    private Institution institutionDomain;

    public static Member convertNewForm(MemberForm form, Institution institution, String imagePath) {
        Member m = new Member();
        m.setInstitutionDomain(institution);
        m.setPhoto(imagePath);
        return convertUpdateForm(form, m);
    }

    public static Member convertUpdateForm(MemberForm form, Member m) {
        m.setName(form.getName());
        m.setTitle(form.getTitle());
        m.setQualification(form.getQualification());
        m.setEducation(form.getEducation());
        return m;
    }
}

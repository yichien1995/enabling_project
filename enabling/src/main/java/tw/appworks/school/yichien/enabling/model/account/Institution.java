package tw.appworks.school.yichien.enabling.model.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.appworks.school.yichien.enabling.dto.form.NewInstitutionForm;

@Entity
@Table(name = "institution")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "domain_name", nullable = false, unique = true)
    private String domainName;

    @Column(name = "institution_name", columnDefinition = "varchar(50) default '輸入機構名稱'")
    private String institutionName;

    @Column(name = "address", columnDefinition = "varchar(255) default '輸入機構地址'")
    private String address;

    @Column(name = "tel", columnDefinition = "varchar(255) default '輸入機構電話'")
    private String tel;

    @Column(name = "business_hour", columnDefinition = "varchar(255) default '輸入營業時間'")
    private String businessHour;

    @Column(name = "webpage_available", columnDefinition = "int default 0")
    private Integer webpageAvailable;

    public Institution(String domainName, String institutionName, String address, String tel, String businessHour, Integer webpageAvailable) {
        this.domainName = domainName;
        this.institutionName = institutionName;
        this.address = address;
        this.tel = tel;
        this.businessHour = businessHour;
        this.webpageAvailable = webpageAvailable;
    }

    public static Institution convertNewForm(NewInstitutionForm form) {
        Institution i = new Institution();
        i.setDomainName(form.getInstitutionDomain());
        String institutionName =
                (form.getInstitutionName() == null || form.getInstitutionName().isEmpty()) ? "我的新機構" : form.getInstitutionName();
        String tel =
                (form.getTel() == null || form.getTel().isEmpty()) ? "輸入機構電話" : form.getTel();
        String address =
                (form.getAddress() == null || form.getAddress().isEmpty()) ? "輸入機構地址" : form.getAddress();
        String businessHour =
                (form.getBusinessHour() == null || form.getBusinessHour().isEmpty()) ? "輸入營業時間" : form.getBusinessHour();

        i.setInstitutionName(institutionName);
        i.setTel(tel);
        i.setAddress(address);
        i.setBusinessHour(businessHour);
        i.setWebpageAvailable(0);
        return i;
    }

    public static Institution convertUpdateForm(Institution form, Institution i) {
        i.setInstitutionName(form.getInstitutionName());
        i.setAddress(form.getAddress());
        i.setTel(form.getTel());
        i.setBusinessHour(form.getBusinessHour());
        return i;
    }
}

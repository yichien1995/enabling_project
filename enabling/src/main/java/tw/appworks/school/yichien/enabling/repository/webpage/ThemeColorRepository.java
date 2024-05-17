package tw.appworks.school.yichien.enabling.repository.webpage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.webpage.ThemeColor;

@Repository
public interface ThemeColorRepository extends JpaRepository<ThemeColor, Integer> {

    ThemeColor findById(int id);
}

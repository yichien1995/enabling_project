package tw.appworks.school.yichien.enabling.repository.webpage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tw.appworks.school.yichien.enabling.model.webpage.ServiceItem;

import java.util.List;

public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {

	@Query(value = "SELECT * FROM services WHERE institution_domain = :domain", nativeQuery = true)
	List<ServiceItem> getAllServicesItemByDomain(@Param("domain") String domain);

	void deleteServiceItemById(Long id);
}

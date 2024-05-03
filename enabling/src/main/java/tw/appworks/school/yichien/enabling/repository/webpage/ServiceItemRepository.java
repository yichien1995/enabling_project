package tw.appworks.school.yichien.enabling.repository.webpage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.yichien.enabling.model.webpage.ServiceItem;

import java.util.List;

@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {

	@Query(value = "SELECT * FROM services WHERE institution_domain = :domain", nativeQuery = true)
	List<ServiceItem> getAllServicesItemByDomain(@Param("domain") String domain);

	ServiceItem getServiceItemById(Long id);

	void deleteServiceItemById(Long id);
}

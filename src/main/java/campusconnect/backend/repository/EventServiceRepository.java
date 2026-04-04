package campusconnect.backend.repository;

import campusconnect.backend.entity.EventRequest;
import campusconnect.backend.entity.EventService;
import campusconnect.backend.entity.ServiceType;
import campusconnect.backend.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventServiceRepository extends JpaRepository<EventService, Long> {

    Optional<EventService> findByEventRequestAndServiceType(EventRequest eventRequest, ServiceType service);
    List<EventService> findByVendor(Vendor vendor);
    List<EventService> findByEventRequest(EventRequest event);
}

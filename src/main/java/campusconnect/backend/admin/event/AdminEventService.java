package campusconnect.backend.admin.event;

import campusconnect.backend.entity.*;
import campusconnect.backend.repository.EventRequestRepository;
import campusconnect.backend.repository.EventServiceRepository;
import campusconnect.backend.repository.ServiceRepository;
import campusconnect.backend.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class AdminEventService {

    @Autowired
    private EventRequestRepository eventRequestRepo;

    @Autowired
    private EventServiceRepository eventServiceRepo;

    @Autowired
    private VendorRepository vendorRepo;

    @Autowired
    private ServiceRepository serviceRepo;

    public AdminEventDTO mapToDTO(EventRequest event){
        return AdminEventDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .maxParticipants(event.getMaxParticipants())
                .category(event.getCategory())
                .status(event.getEventStatus())
                .collegeId(event.getCollege().getId())
                .collegeName(event.getCollege().getName())
                .build();
    }

    public List<AdminEventDTO> getEvents(EventStatus status){

        List<EventRequest> events;

        if(status != null)
            events = eventRequestRepo.findByEventStatus(status);
        else
            events = eventRequestRepo.findAll();

        return events.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AdminEventDTO getEventById(Long id){
        EventRequest event = eventRequestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("event not found"));

        return mapToDTO(event);
    }

    public AdminEventDTO updateStatus(Long id, EventStatus status){
        EventRequest request = eventRequestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("event not found"));

        request.setEventStatus(status);

        eventRequestRepo.save(request);

        return mapToDTO(request);
    }

    //service-vendor
    public EventService assignVendor(Long service_id, Long vendor_id) {

        EventService eventService = eventServiceRepo.findById(service_id)
                .orElseThrow(() -> new RuntimeException("service not found"));

        if (vendor_id != null) {
            Vendor vendor = vendorRepo.findById(vendor_id)
                    .orElseThrow(() -> new RuntimeException("service not found"));

            eventService.setVendor(vendor);
        }
        else{
            eventService.setVendor(null);
        }

        return eventServiceRepo.save(eventService);

    }

    //vendors of a service
    public List<Vendor> getServiceVendors(Long service_id){

        Service service = serviceRepo.findById(service_id)
                .orElseThrow(()-> new RuntimeException("service not found"));

        List<Vendor> vendors = vendorRepo.findByCategory(service.getService());

        return vendors;
    }

}

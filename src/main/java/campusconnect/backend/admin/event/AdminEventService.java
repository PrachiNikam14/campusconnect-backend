package campusconnect.backend.admin.event;

import campusconnect.backend.admin.vendor.AdminVendorDTO;
import campusconnect.backend.entity.*;
import campusconnect.backend.repository.EventRequestRepository;
import campusconnect.backend.repository.EventServiceRepository;
import campusconnect.backend.repository.ServiceRepository;
import campusconnect.backend.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
    public AdminEventServiceDTO assignVendor(Long eventId, Long serviceId, Long vendorId) {

        EventRequest event = eventRequestRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("event not found"));

        ServiceType service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("service not found"));

        EventService eventService = eventServiceRepo.findByEventRequestAndServiceType(event, service)
                .orElseThrow(() -> new RuntimeException("Event service not found"));

        if (vendorId != null) {
            Vendor vendor = vendorRepo.findById(vendorId)
                    .orElseThrow(() -> new RuntimeException("vendor not found"));

            eventService.setVendor(vendor);
        }
        else{
            eventService.setVendor(null);
        }

        eventServiceRepo.save(eventService);

        return mapToEventServiceDTO(eventService);

    }

    //vendors of a service
    public List<AdminVendorDTO> getServiceVendors(Long serviceId){

        ServiceType service = serviceRepo.findById(serviceId)
                .orElseThrow(()-> new RuntimeException("service not found"));

        List<Vendor> vendors = vendorRepo.findByCategory(service.getService());

        return vendors.stream()
                .map(this::mapToVendorDTO)
                .collect(Collectors.toList());
    }

    public List<AdminEventServiceDTO> getEventServices(){

        List<EventService> eventServices = eventServiceRepo.findAll();

        return eventServices.stream()
                .map(this::mapToEventServiceDTO)
                .collect(Collectors.toList());
    }

    public AdminEventServiceDTO getEventServiceById(Long id){

        EventService eventService =  eventServiceRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("service not found"));

        return mapToEventServiceDTO(eventService);
    }

    public AdminVendorDTO mapToVendorDTO(Vendor vendor){
        return AdminVendorDTO.builder()
                .id(vendor.getId())
                .businessName(vendor.getBusinessName())
                .category(vendor.getCategory())
                .phone(vendor.getPhone())
                .gstNumber(vendor.getGstNumber())
                .businessLicenseUrl(vendor.getBusinessLicenseUrl())
                .verificationStatus(vendor.getVerificationStatus())
                .userId(vendor.getUser().getId())
                .userName(vendor.getUser().getName())
                .userEmail(vendor.getUser().getEmail())
                .userEnabled(vendor.getUser().isEnabled())
                .build();
    }

    public AdminEventServiceDTO mapToEventServiceDTO(EventService eventService){
        return AdminEventServiceDTO.builder()
                .id(eventService.getId()).
                title(eventService.getEventRequest().getTitle())
                .serviceName(eventService.getServiceType().getService())
                .vendor(eventService.getVendor() != null ? eventService.getVendor().getBusinessName() : null)
                .build();
    }

}

package campusconnect.backend.publicFolder;

import campusconnect.backend.entity.EventRequest;
import campusconnect.backend.entity.EventStatus;
import campusconnect.backend.repository.EventRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicService {

    private final EventRequestRepository eventRepo;

    public PublicEventDTO mapToDTO(EventRequest event){
        return PublicEventDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .maxParticipants(event.getMaxParticipants())
                .bannerUrl(event.getBannerUrl())
                .category(event.getCategory())
                .eventStatus(event.getEventStatus())
                .isPaid(event.isPaid())
                .price(event.getPrice())
                .collegeId(event.getCollege().getId())
                .collegeName(event.getCollege().getName())
                .build();
    }

    public List<PublicEventDTO> getEvents(){
        List<EventRequest> events = eventRepo.findByEventStatusIn(Arrays.asList(EventStatus.BOOKED, EventStatus.CONFIRMED));
        return events.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PublicEventDTO getEventById(Long id){
        return mapToDTO(eventRepo.findById(id).orElseThrow(()-> new RuntimeException("Event not found")));
    }
}

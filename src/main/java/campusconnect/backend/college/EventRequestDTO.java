package campusconnect.backend.college;

import campusconnect.backend.entity.EventCategory;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventRequestDTO {

    private String title;

    private String description;

    private LocalDateTime eventDate;

    private int maxParticipants;

    private EventCategory category;

    private List<Long> serviceIds; // services required

    private boolean isPaid;
    private Double price;

}

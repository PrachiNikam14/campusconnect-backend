package campusconnect.backend.publicFolder;

import campusconnect.backend.entity.EventCategory;
import campusconnect.backend.entity.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicEventDTO {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime eventDate;

    private int maxParticipants;

    private String bannerUrl;

    private EventCategory category;

    private EventStatus eventStatus;

    private boolean isPaid;

    private Double price;

    private Long collegeId;

    private String collegeName;
}

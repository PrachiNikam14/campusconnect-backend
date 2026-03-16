package campusconnect.backend.admin.registration;

import campusconnect.backend.repository.EventRegistrationRepository;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegistrationDTO {

    private Long id;

    private Long studentId;
    private String studentName;
    private String studentEmail;

    private Long eventId;
    private String eventTitle;
    private LocalDateTime eventDate;
    private int maxParticipants;
    private String eventCategory;

    private LocalDateTime registeredAt;

    private boolean paymentDone;

    private double paidAmount = 0;


}

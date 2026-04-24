package campusconnect.backend.vendor;

import java.time.LocalDateTime;

public class VendorHistoryDTO {

    private Long id;
    private String title;
    private LocalDateTime eventDate;
    private Double price;
    private String eventStatus;
    private String collegeName;

    public VendorHistoryDTO(Long id, String title, LocalDateTime eventDate,
                            Double price, String eventStatus, String collegeName) {
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.price = price;
        this.eventStatus = eventStatus;
        this.collegeName = collegeName;
    }

    // getters
}
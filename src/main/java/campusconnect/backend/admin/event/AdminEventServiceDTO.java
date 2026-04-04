package campusconnect.backend.admin.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminEventServiceDTO {

    private Long id;
    private Long eventId;
    private Long serviceTypeId;
    private String title;
    private String serviceName;
    private String vendor;

}

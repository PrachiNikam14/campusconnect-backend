package campusconnect.backend.admin.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardDTO {
    private long totalColleges;
    private long totalVendors;
    private long totalStudents;
    private long totalEvents;
    private long totalUsers;

    private long pendingVendors;
    private long pendingColleges;
    private long pendingStudents;
    private long pendingEventRequests;
}

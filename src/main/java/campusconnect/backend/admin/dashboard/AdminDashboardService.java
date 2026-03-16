package campusconnect.backend.admin.dashboard;

import campusconnect.backend.entity.EventRequest;
import campusconnect.backend.entity.EventStatus;
import campusconnect.backend.entity.VerificationStatus;
import campusconnect.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final CollegeRepository collegeRepository;
    private final VendorRepository vendorRepository;
    private final StudentRepository studentRepository;
    private final EventRequestRepository eventRequestRepository;
    private final UserRepository userRepository;

    public AdminDashboardDTO getDashboardData(){
        AdminDashboardDTO dto = new AdminDashboardDTO();

        dto.setTotalColleges(collegeRepository.count());
        dto.setTotalVendors(vendorRepository.count());
        dto.setTotalStudents(studentRepository.count());
        dto.setTotalEvents(eventRequestRepository.count());
        dto.setTotalUsers(userRepository.count());

        dto.setPendingColleges(collegeRepository.countByVerificationStatus(VerificationStatus.PENDING));
        dto.setPendingVendors(vendorRepository.countByVerificationStatus(VerificationStatus.PENDING));
        dto.setPendingStudents(studentRepository.countByVerificationStatus(VerificationStatus.PENDING));
        dto.setPendingEventRequests(eventRequestRepository.countByEventStatus(EventStatus.PENDING));

        return dto;
    }
}

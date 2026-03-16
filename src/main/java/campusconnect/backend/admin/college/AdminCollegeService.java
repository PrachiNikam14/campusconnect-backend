package campusconnect.backend.admin.college;

import campusconnect.backend.entity.College;
import campusconnect.backend.entity.VerificationStatus;
import campusconnect.backend.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminCollegeService {

    @Autowired
    public CollegeRepository collegeRepo;

    public AdminCollegeDTO mapToDTO(College college){
        return AdminCollegeDTO.builder()
                .id(college.getId())
                .name(college.getName())
                .universityName(college.getUniversityname())
                .city(college.getCity())
                .website(college.getWebsite())
                .officialLetterUrl(college.getOfficialLetterUrl())
                .naacCertificateUrl(college.getNaacCertificateUrl())
                .logoUrl(college.getLogoUrl())
                .verificationStatus(college.getVerificationStatus())
                .userId(college.getUser().getId())
                .userEmail(college.getUser().getEmail())
                .userEnabled(college.getUser().isEnabled())
                .build();
    }

    public List<AdminCollegeDTO> getAllColleges(VerificationStatus status){

        List<College> colleges;
        if(status != null)
            colleges = collegeRepo.findByVerificationStatus(status);
        else
            colleges = collegeRepo.findAll();

        return colleges.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AdminCollegeDTO getUserById(Long id){
        College college = collegeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("'college not found"));
        return mapToDTO(college);
    }

    public AdminCollegeDTO verifyStatus(Long id, VerificationStatus status){

        College college = collegeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("'college not found"));

        college.setVerificationStatus(status);

        collegeRepo.save(college);

        return mapToDTO(college);
    }


}

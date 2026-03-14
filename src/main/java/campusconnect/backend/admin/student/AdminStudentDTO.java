package campusconnect.backend.admin.student;

import campusconnect.backend.entity.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminStudentDTO {

    private Long id;
    private String rollNumber;
    private String department;
    private int year;
    private String bio;
    private String skills;
    private String hobbies;
    private String linkedinUrl;
    private String githubUrl;
    private String profilePhoto;
    private String idCardUrl;
    private VerificationStatus verificationStatus;
    private Long userId;
    private String userName;
    private String userEmail;
    private Long CollegeId;
    private String CollegeName;
}

package campusconnect.backend.student;

import jakarta.persistence.ElementCollection;
import lombok.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;
import campusconnect.backend.entity.VerificationStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {

    private String name;
    private String email;

    @Size(max = 300)
    private String bio;

    @ElementCollection
    private List<String> skills;

    private String hobbies;

    private String linkedinUrl;

    private String githubUrl;

    private String rollNumber;

    private String department;

    @Min(1)
    @Max(4)
    private Integer year;

    private MultipartFile profilePhoto;  // for uploading new photo
    private String profilePhotoUrl;      // for sending URL to frontend


    private MultipartFile idCard;

    private Long collegeId;


}
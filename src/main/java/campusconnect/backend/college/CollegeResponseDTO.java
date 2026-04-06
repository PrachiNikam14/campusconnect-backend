package campusconnect.backend.college;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollegeResponseDTO {

    private Long id;
    private String name;
    private String universityname;
    private String city;
    private String website;

    private String logoUrl;

    // 🔥 ADD THESE
    private String naacCertificateUrl;
    private String officialLetterUrl;

    private String verificationStatus;

    private String email;
    private String createdAt;
}
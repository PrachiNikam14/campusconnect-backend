package campusconnect.backend.admin.college;

import campusconnect.backend.entity.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCollegeDTO {

        private Long id;

        private String name;

        private String universityName;

        private String city;

        private String website;

        private String officialLetterUrl;

        private String naacCertificateUrl;

        private String logoUrl;

        private VerificationStatus verificationStatus;

        private Long userId;

        private String userEmail;

        private boolean userEnabled;

    }


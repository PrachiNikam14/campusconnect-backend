package campusconnect.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String universityname;

    private String city;

    private String website;

    private String officialLetterUrl;
    private String officialLetterPublicId;   // ADD

    private String naacCertificateUrl;
    private String naacCertificatePublicId;  // ADD

    private String logoUrl;
    private String logoPublicId;


    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
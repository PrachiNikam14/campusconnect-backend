package campusconnect.backend.vendor;

import campusconnect.backend.entity.User;
import campusconnect.backend.entity.Vendor;
import campusconnect.backend.entity.VerificationStatus;
import campusconnect.backend.repository.UserRepository;
import campusconnect.backend.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private VendorRepository vendorRepo;

    @PostMapping("/register")
    public String register(@RequestBody VendorProfileRequest request,
                           Authentication authentication)
    {
        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Vendor vendor = Vendor.builder()
                .businessName(request.getBusinessName())
                .category(request.getCategory())
                .phone(request.getPhone())
                .gstNumber(request.getGstNumber())
                .businessLicenseUrl(request.getBusinessLicenseUrl())
                .verificationStatus(VerificationStatus.PENDING)
                .user(user)
                .build();

        vendorRepo.save(vendor);

        return "vendor register successfully";
    }
    @GetMapping("/profile")
    public Vendor getProfile(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return vendorRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("vendor not found"));
    }

    @PutMapping("/profile")
    public Vendor updateProfile(@RequestBody VendorProfileRequest request,
                                Authentication authentication) {

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Vendor vendor = vendorRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("vendor not found"));

        vendor.setBusinessName(request.getBusinessName());
        vendor.setCategory(request.getCategory());
        vendor.setPhone(request.getPhone());
        vendor.setGstNumber(request.getGstNumber());
        vendor.setBusinessLicenseUrl(request.getBusinessLicenseUrl());

        return vendorRepo.save(vendor);
    }

    @GetMapping("/status")
    public String getStatus(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Vendor vendor = vendorRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("vendor not found"));

        return vendor.getVerificationStatus().toString();
    }

}

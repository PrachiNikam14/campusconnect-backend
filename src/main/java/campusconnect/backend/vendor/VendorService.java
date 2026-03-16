package campusconnect.backend.vendor;

import campusconnect.backend.common.storage.dto.FileUploadResponse;
import campusconnect.backend.common.storage.service.FileUploadService;
import campusconnect.backend.entity.User;
import campusconnect.backend.entity.Vendor;
import campusconnect.backend.entity.VerificationStatus;
import campusconnect.backend.repository.UserRepository;
import campusconnect.backend.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Service
public class VendorService {


    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;

    private final FileUploadService fileUploadService;

    public Vendor registerVendor(String email, VendorProfileRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (vendorRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("Vendor profile already exists");
        }

        Vendor vendor = Vendor.builder()
                .businessName(request.getBusinessName())
                .category(request.getCategory())
                .phone(request.getPhone())
                .gstNumber(request.getGstNumber())
                .businessLicenseUrl(request.getBusinessLicenseUrl())
                .verificationStatus(VerificationStatus.PENDING)
                .user(user)
                .build();

        return vendorRepository.save(vendor);
    }

    public Vendor uploadBusinessLicense(String email, MultipartFile file) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Vendor vendor = vendorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Vendor profile not found"));

        // Upload to Cloudinary
        FileUploadResponse response =
                fileUploadService.uploadFile(
                        file,
                        "campusconnect/vendors/documents"
                );

        vendor.setBusinessLicenseUrl(response.getUrl());
        vendor.setBusinessLicensePublicId(response.getPublicId());

        return vendorRepository.save(vendor);
    }

    public Vendor getVendorProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return vendorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Vendor profile not found"));
    }

    public Vendor updateVendorProfile(String email, VendorProfileRequest request) {

        Vendor vendor = getVendorProfile(email);

        vendor.setBusinessName(request.getBusinessName());
        vendor.setCategory(request.getCategory());
        vendor.setPhone(request.getPhone());
        vendor.setGstNumber(request.getGstNumber());
        vendor.setBusinessLicenseUrl(request.getBusinessLicenseUrl());

        return vendorRepository.save(vendor);
    }

    public String getVerificationStatus(String email) {

        Vendor vendor = getVendorProfile(email);

        return vendor.getVerificationStatus().name();
    }
}
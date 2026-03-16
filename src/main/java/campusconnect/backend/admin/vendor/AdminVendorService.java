package campusconnect.backend.admin.vendor;

import campusconnect.backend.entity.ServiceType;
import campusconnect.backend.entity.Vendor;
import campusconnect.backend.entity.VerificationStatus;
import campusconnect.backend.repository.ServiceRepository;
import campusconnect.backend.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminVendorService {

    private final VendorRepository vendorRepo;
    private final ServiceRepository serviceRepo;

    public AdminVendorDTO mapToDTO(Vendor vendor) {

        return AdminVendorDTO.builder()
                .id(vendor.getId())
                .businessName(vendor.getBusinessName())
                .category(vendor.getCategory())
                .phone(vendor.getPhone())
                .gstNumber(vendor.getGstNumber())
                .businessLicenseUrl(vendor.getBusinessLicenseUrl())
                .verificationStatus(vendor.getVerificationStatus())
                .userId(vendor.getUser() != null ? vendor.getUser().getId() : null)
                .userName(vendor.getUser() != null ? vendor.getUser().getName() : null)
                .userEmail(vendor.getUser() != null ? vendor.getUser().getEmail() : null)
                .userEnabled(vendor.getUser() != null ? vendor.getUser().isEnabled() : null)
                .build();
    }

    public List<AdminVendorDTO> getVendors(VerificationStatus status) {

        List<Vendor> vendors;

        if (status != null) {
            vendors = vendorRepo.findByVerificationStatus(status);
        } else {
            vendors = vendorRepo.findAll();
        }

        return vendors.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AdminVendorDTO getVendorById(Long id) {

        Vendor vendor = vendorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        return mapToDTO(vendor);
    }

    public AdminVendorDTO verifyStatus(Long id, VerificationStatus status) {

        Vendor vendor = vendorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.setVerificationStatus(status);

        // If admin approves vendor
        if (status == VerificationStatus.APPROVED) {

            Optional<ServiceType> existingService =
                    serviceRepo.findByService(vendor.getCategory());

            if (existingService.isEmpty()) {

                ServiceType serviceType = ServiceType.builder()
                        .service(vendor.getCategory())
                        .build();

                serviceRepo.save(serviceType);
            }
        }

        vendorRepo.save(vendor);

        return mapToDTO(vendor);
    }
}
package campusconnect.backend.admin.vendor;

import campusconnect.backend.entity.Vendor;
import campusconnect.backend.entity.VerificationStatus;
import campusconnect.backend.repository.ServiceRepository;
import campusconnect.backend.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import campusconnect.backend.entity.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class AdminVendorService {

    @Autowired
    private VendorRepository vendorRepo;

    @Autowired
    private ServiceRepository serviceRepo;

    public AdminVendorDTO mapToDTO(Vendor vendor) {
        return AdminVendorDTO.builder()
                .id(vendor.getId())
                .businessName(vendor.getBusinessName())
                .category(vendor.getCategory())
                .phone(vendor.getPhone())
                .gstNumber(vendor.getGstNumber())
                .businessLicenseUrl(vendor.getBusinessLicenseUrl())
                .verificationStatus(vendor.getVerificationStatus())
                .userId(vendor.getUser().getId())
                .userName(vendor.getUser().getName())
                .userEmail(vendor.getUser().getEmail())
                .build();
    }

    public List<AdminVendorDTO> getVendors(VerificationStatus status) {
        List<Vendor> vendors;

        if (status != null)
            vendors = vendorRepo.findByVerificationStatus(status);
        else
            vendors = vendorRepo.findAll();

        return vendors.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AdminVendorDTO getVendorById(Long id) {
        Vendor vendor = vendorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("vendor not found"));

        return mapToDTO(vendor);
    }

    public AdminVendorDTO verifyStatus(Long id, VerificationStatus status) {
        Vendor vendor = vendorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("vendor not found"));

        if (status == VerificationStatus.APPROVED) {

            Optional<Service> existingService =
                    serviceRepo.findByService(vendor.getCategory());

            if (existingService.isEmpty()) {
                Service service = Service.builder()
                        .service(vendor.getCategory())
                        .build();

                serviceRepo.save(service);
            }
        }
            vendor.setVerificationStatus(status);
            vendorRepo.save(vendor);

            return mapToDTO(vendor);
        }

    }

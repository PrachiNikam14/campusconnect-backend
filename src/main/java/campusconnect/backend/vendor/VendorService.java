package campusconnect.backend.vendor;

import campusconnect.backend.common.storage.dto.FileUploadResponse;
import campusconnect.backend.common.storage.service.FileUploadService;
import campusconnect.backend.entity.EventService;
import campusconnect.backend.entity.User;
import campusconnect.backend.entity.Vendor;
import campusconnect.backend.entity.VerificationStatus;
import campusconnect.backend.repository.EventServiceRepository;
import campusconnect.backend.repository.UserRepository;
import campusconnect.backend.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VendorService {

    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final FileUploadService fileUploadService;
    private final EventServiceRepository eventServiceRepository;

    // ================= DTO CONVERSION =================
    private VendorHistoryDTO convertToDTO(EventService eventService) {
        return new VendorHistoryDTO(
                eventService.getId(),
                eventService.getEventRequest().getTitle(),
                eventService.getEventRequest().getEventDate(),
                eventService.getEventRequest().getPrice(),
                eventService.getEventRequest().getEventStatus().toString(),
                eventService.getEventRequest().getCollege().getName()
        );
    }

    // ================= REGISTER =================
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

    // ================= LICENSE UPLOAD =================
    public Vendor uploadBusinessLicense(String email, MultipartFile file) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Vendor vendor = vendorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Vendor profile not found"));

        FileUploadResponse response =
                fileUploadService.uploadFile(file, "campusconnect/vendors/documents");

        vendor.setBusinessLicenseUrl(response.getUrl());
        vendor.setBusinessLicensePublicId(response.getPublicId());

        return vendorRepository.save(vendor);
    }

    // ================= PROFILE =================
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

    // ================= BROCHURE =================
    public Vendor uploadBrochure(String email, MultipartFile file){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Vendor vendor = vendorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Vendor profile not found"));

        if (vendor.getBrochurePublicId() != null) {
            fileUploadService.deleteFile(vendor.getBrochurePublicId());
        }

        FileUploadResponse response =
                fileUploadService.uploadFile(file, "campusconnect/vendors/documents");

        vendor.setBrochureUrl(response.getUrl());
        vendor.setBrochurePublicId(response.getPublicId());

        return vendorRepository.save(vendor);
    }

    // ================= STATUS =================
    public String getVerificationStatus(String email) {
        Vendor vendor = getVendorProfile(email);
        return vendor.getVerificationStatus().name();
    }

    // ================= HISTORY (UPDATED) =================
    public List<VendorHistoryDTO> getVendorHistory(String email) {

        Vendor vendor = getVendorProfile(email);

        return eventServiceRepository.findByVendor(vendor)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
}
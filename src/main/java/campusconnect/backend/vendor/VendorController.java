package campusconnect.backend.vendor;

import campusconnect.backend.entity.EventService;
import campusconnect.backend.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    // Register Vendor
    @PostMapping("/register")
    public Vendor registerVendor(@RequestBody VendorProfileRequest request,
                                 Authentication authentication) {

        return vendorService.registerVendor(authentication.getName(), request);
    }

    // Get Vendor Profile
    @GetMapping("/profile")
    public Vendor getVendorProfile(Authentication authentication) {

        return vendorService.getVendorProfile(authentication.getName());
    }

    // Update Vendor Profile
    @PutMapping("/profile")
    public Vendor updateVendorProfile(@RequestBody VendorProfileRequest request,
                                      Authentication authentication) {

        return vendorService.updateVendorProfile(authentication.getName(), request);
    }

    // Upload Brochure PDF
    @PostMapping("/upload-brochure")
    public Vendor uploadBrochure(@RequestParam("file") MultipartFile file,
                                 Authentication authentication) {

        return vendorService.uploadBrochure(authentication.getName(), file);
    }

    // Get Verification Status
    @GetMapping("/status")
    public String getVerificationStatus(Authentication authentication) {

        return vendorService.getVerificationStatus(authentication.getName());
    }

    // NEW API
    @PostMapping("/upload-license")
    public Vendor uploadBusinessLicense(
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return vendorService.uploadBusinessLicense(email, file);
    }

    // Vendor Event History
    @GetMapping("/history")
    public List<EventService> getVendorHistory(Authentication authentication) {

        return vendorService.getVendorHistory(authentication.getName());
    }
}
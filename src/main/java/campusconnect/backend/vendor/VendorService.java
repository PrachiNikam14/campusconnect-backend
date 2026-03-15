package campusconnect.backend.vendor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import campusconnect.backend.entity.User;
import campusconnect.backend.entity.Vendor;
import campusconnect.backend.entity.VerificationStatus;
import campusconnect.backend.repository.VendorRepository;
import campusconnect.backend.vendor.VendorProfileRequest;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    public Vendor createVendorProfile(User user, VendorProfileRequest request) {

        Vendor vendor = new Vendor();

        vendor.setBusinessName(request.getBusinessName());
        vendor.setCategory(request.getCategory());
        vendor.setPhone(request.getPhone());
        vendor.setGstNumber(request.getGstNumber());
        vendor.setBusinessLicenseUrl(request.getBusinessLicenseUrl());

        vendor.setUser(user);
        vendor.setVerificationStatus(VerificationStatus.PENDING);

        return vendorRepository.save(vendor);
    }

}

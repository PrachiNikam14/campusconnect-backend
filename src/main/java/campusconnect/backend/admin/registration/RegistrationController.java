package campusconnect.backend.admin.registration;

import lombok.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/admin/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/registrations")
    public Page<RegistrationDTO> getRegistrations(
            @RequestParam(required = false) Long eventId,
            Pageable pageable) {

        return registrationService.getRegistrations(eventId, pageable);
    }

    @GetMapping("/registrations/count")
    public long getRegistrationsCount(
            @RequestParam(required = false) Long eventId) {

        return registrationService.getRegistrationsCount(eventId);
    }

    @GetMapping("/registrations/{registrationId}")
    public RegistrationDTO getRegistration(
            @PathVariable Long registrationId) {

        return registrationService.getRegistration(registrationId);
    }
}

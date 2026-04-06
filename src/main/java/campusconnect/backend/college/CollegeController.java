    package campusconnect.backend.college;

    import campusconnect.backend.dto.EventPaymentDTO;
    import campusconnect.backend.entity.College;
    import campusconnect.backend.entity.EventCategory;
    import campusconnect.backend.entity.ServiceType;
    import campusconnect.backend.repository.ServiceRepository;
    import jakarta.mail.MessagingException;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.Authentication;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.security.Principal;
    import java.time.LocalDateTime;
    import java.util.Arrays;
    import java.util.List;

    @RestController
    @RequestMapping("/college")
    @RequiredArgsConstructor
    public class CollegeController {

        private final CollegeService collegeService;
        private final ServiceRepository serviceRepository;

        @PostMapping("/register")
        public String registerCollege(
                @RequestBody CollegeRegistrationRequestDTO request,
                Authentication authentication
        ) throws MessagingException {

            String email = authentication.getName();

            return collegeService.registerCollege(request, email);
        }

        @GetMapping("/profile")
        public CollegeResponseDTO getCollegeProfile(Authentication authentication) {

            String email = authentication.getName();

            return collegeService.getCollegeByUser(email);
        }

        @PatchMapping("/update")
        public CollegeResponseDTO updateCollegeProfile(
                @RequestBody CollegeUpdateDTO request,
                Authentication authentication
        ) {

            String email = authentication.getName();

            return collegeService.updateCollegeProfile(request, email);
        }

        @PostMapping("/event-request")
        public EventRequestResponseDTO createEventRequest(
                @ModelAttribute EventRequestDTO request,
                @RequestParam(required = false) MultipartFile banner,
                Authentication authentication
        ) {

            String email = authentication.getName();

            return collegeService.createEventRequest(request, banner, email);
        }

        @GetMapping("/event-requests")
        public List<EventRequestResponseDTO> getEventRequests(Authentication authentication) {
            String email;
            try {
                email = authentication.getName();
                System.out.println("EMAIL: " + email);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

            return collegeService.getCollegeEventRequests(email);
        }

        @DeleteMapping("/event-requests/{id}")
        public String deleteEventRequest(
                @PathVariable Long id,
                Authentication authentication
        ) {

            String email = authentication.getName();

            return collegeService.deleteEventRequest(id, email);
        }

        @PutMapping("/event-requests/{id}")
        public EventRequestResponseDTO updateEventRequest(
                @PathVariable Long id,
                @RequestBody EventRequestDTO request,
                Authentication authentication
        ) {

            String email = authentication.getName();

            return collegeService.updateEventRequest(id, request, email);
        }

        @PostMapping("/events/{id}/confirm")
        public String confirmEvent(@PathVariable Long id,
                                   @RequestBody EventPaymentDTO payment,
                                   Authentication authentication) throws Exception {

            return collegeService.confirmEventPlan(
                    id,
                    payment,
                    authentication.getName()
            );
        }

        @PostMapping("/events/{id}/reject")
        public String rejectEvent(@PathVariable Long id,
                                  Authentication authentication) {

            return collegeService.rejectEventPlan(
                    id,
                    authentication.getName()
            );
        }

        @PutMapping("/events/{id}/reschedule")
        public String rescheduleEvent(@PathVariable Long id,
                                      @RequestParam LocalDateTime newDate,
                                      Authentication authentication) {

            return collegeService.requestReschedule(
                    id,
                    newDate,
                    authentication.getName()
            );
        }

//        @PostMapping("/upload-official-letter")
//        public College uploadOfficialLetter(
//                @RequestParam MultipartFile file,
//                Authentication authentication
//        ) {
//
//            return collegeService.uploadOfficialLetter(authentication.getName(), file);
//        }
//
//        @PostMapping("/upload-naac")
//        public College uploadNaac(
//                @RequestParam MultipartFile file,
//                Authentication authentication
//        ) {
//
//            return collegeService.uploadNaacCertificate(authentication.getName(), file);
//        }
//
//        @PostMapping("/upload-logo")
//        public College uploadLogo(
//                @RequestParam MultipartFile file,
//                Authentication authentication
//        ) {
//
//            return collegeService.uploadLogo(authentication.getName(), file);
//        }

        @PostMapping("/upload")
        public ResponseEntity<?> uploadDocument(
                @RequestParam("file") MultipartFile file,
                @RequestParam("type") String type,
                Principal principal
        ) {
            return ResponseEntity.ok(
                    collegeService.uploadDocument(principal.getName(), file, type)
            );
        }

        @GetMapping("/event-categories")
        public List<String> getCategories() {
            return Arrays.stream(EventCategory.values())
                    .map(Enum::name)
                    .toList();
        }

        @GetMapping("/services")
        public ResponseEntity<List<ServiceType>> getAllServices() {
            return ResponseEntity.ok(serviceRepository.findAll());
        }
    }
package campusconnect.backend.admin.registration;

import campusconnect.backend.entity.EventRegistration;
import campusconnect.backend.repository.EventRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final EventRegistrationRepository eventRegistrationRepo;

    public Page<RegistrationDTO> getRegistrations(Long eventId, Pageable pageable) {

        Page<EventRegistration> registrations;

        if (eventId != null) {
            registrations = eventRegistrationRepo.findByEvent_Id(eventId, pageable);
        } else {
            registrations = eventRegistrationRepo.findAll(pageable);
        }

        return registrations.map(this::convertToDTO);
    }

    public Long getRegistrationsCount(Long eventId) {

        if (eventId != null) {
            return eventRegistrationRepo.countByEvent_Id(eventId);
        }

        return eventRegistrationRepo.count();
    }

    public RegistrationDTO getRegistration(Long registrationId) {

        EventRegistration reg = eventRegistrationRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        return convertToDTO(reg);
    }

    private RegistrationDTO convertToDTO(EventRegistration reg) {

        RegistrationDTO dto = new RegistrationDTO();

        dto.setId(reg.getId());

        dto.setStudentId(reg.getStudent().getId());
        dto.setStudentName(reg.getStudent().getUser().getName());
        dto.setStudentEmail(reg.getStudent().getUser().getEmail());

        dto.setEventId(reg.getEvent().getId());
        dto.setEventTitle(reg.getEvent().getTitle());
        dto.setEventDate(reg.getEvent().getEventDate());
        dto.setMaxParticipants(reg.getEvent().getMaxParticipants());
        dto.setEventCategory(reg.getEvent().getCategory().toString());

        dto.setRegisteredAt(reg.getRegisteredAt());
        dto.setPaymentDone(reg.isPaymentDone());
        dto.setPaidAmount(reg.getPaidAmount());

        return dto;
    }
}
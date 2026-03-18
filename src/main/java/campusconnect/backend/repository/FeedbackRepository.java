package campusconnect.backend.repository;

import campusconnect.backend.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    boolean existsByStudentIdAndEventId(Long studentId, Long eventId);

}

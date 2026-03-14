package campusconnect.backend.admin.student;

import campusconnect.backend.entity.VerificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/students")
public class AdminStudentController {

    @Autowired
    private AdminStudentService adminStudentService;

    @GetMapping()
    public ResponseEntity<List<AdminStudentDTO>> getStudents(@RequestParam(required = false)
                                                                 VerificationStatus status){
        return ResponseEntity.ok(adminStudentService.getStudents(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminStudentDTO> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(adminStudentService.getStudentById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AdminStudentDTO> verifyStudent(@PathVariable Long id,
                                                         @RequestParam VerificationStatus status){
        return ResponseEntity.ok(adminStudentService.verifyStatus(id, status));
    }
}

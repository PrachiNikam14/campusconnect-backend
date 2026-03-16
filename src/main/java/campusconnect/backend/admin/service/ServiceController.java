package campusconnect.backend.admin.service;

import campusconnect.backend.entity.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/services")
public class ServiceController {

    @Autowired
    private ServiceImpl serviceImpl;

    @GetMapping()
    public ResponseEntity<List<ServiceType>> getServices(){
        return ResponseEntity.ok(serviceImpl.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceType> getServiceById(@PathVariable Long id){
        return ResponseEntity.ok(serviceImpl.getServiceById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceImpl.deleteServiceById(id));
    }
}

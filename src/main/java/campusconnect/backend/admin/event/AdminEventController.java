package campusconnect.backend.admin.event;

import campusconnect.backend.entity.EventService;
import campusconnect.backend.entity.EventStatus;
import campusconnect.backend.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
public class AdminEventController {

    @Autowired
    private AdminEventService adminEventService;

    @GetMapping
    public ResponseEntity<List<AdminEventDTO>> getEvents(@RequestParam(required = false)
                                                         EventStatus status)
    {
        return ResponseEntity.ok(adminEventService.getEvents(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminEventDTO> getEventById(@PathVariable Long id)
    {
        return ResponseEntity.ok(adminEventService.getEventById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdminEventDTO> updateStatus(@PathVariable Long id,
                                                      @RequestParam EventStatus status)
    {
        return ResponseEntity.ok(adminEventService.updateStatus(id, status));
    }

    @PatchMapping("/service-vendor/{service_id}")
    public ResponseEntity<EventService> assignVendor(@PathVariable Long service_id,
                                                     @RequestParam(required = false) Long vendorId)
    {
        return  ResponseEntity.ok(adminEventService.assignVendor(service_id, vendorId));
    }

    @GetMapping("/services/{service_id}/vendors")
    public ResponseEntity<List<Vendor>> getServiceVendors(@PathVariable Long service_id)
    {
        return  ResponseEntity.ok(adminEventService.getServiceVendors(service_id));
    }

}

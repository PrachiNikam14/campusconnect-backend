package campusconnect.backend.publicFolder;

import campusconnect.backend.entity.EventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/")
public class PublicController {

    private final PublicService publicService;

    @GetMapping("events")
    public ResponseEntity<List<PublicEventDTO>> getEvents(){
        return ResponseEntity.ok(publicService.getEvents());
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<PublicEventDTO> getEventById(@PathVariable Long id){
        return ResponseEntity.ok(publicService.getEventById(id));
    }
}

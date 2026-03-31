package campusconnect.backend.dto;

import campusconnect.backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private Role role;

}
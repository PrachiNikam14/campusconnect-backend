package campusconnect.backend.admin.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import campusconnect.backend.entity.Role;
import campusconnect.backend.entity.User;
import campusconnect.backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public List<UserDTO> getUsers(Role role) {

        List<User> users;

        if (role != null) {
            users = userRepo.findByRole(role);
        } else {
            users = userRepo.findAll();
        }

        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToDTO(user);
    }

    public UserDTO setUserStatus(Long id, boolean enabled) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEnabled(enabled);
        userRepo.save(user);

        return convertToDTO(user);
    }

    public List<UserDTO> getBlockedUsers(){
        List<User> users = userRepo.findByEnabledFalse();

        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    private UserDTO convertToDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
    }
}

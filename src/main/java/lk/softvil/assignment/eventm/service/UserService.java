package lk.softvil.assignment.eventm.service;

import lk.softvil.assignment.eventm.dto.UserRegistrationRequest;
import lk.softvil.assignment.eventm.dto.UserResponse;
import lk.softvil.assignment.eventm.model.enums.UserRole;
import org.springframework.data.domain.Page;
import java.util.UUID;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest request);
    UserResponse getUserById(UUID id);
    Page<UserResponse> getAllUsers(int page, int size);
    Page<UserResponse> getUsersByRole(UserRole role, int page, int size);
}
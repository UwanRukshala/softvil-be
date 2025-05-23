package lk.softvil.assignment.eventm.dto;

import lk.softvil.assignment.eventm.model.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String role,  // String representation of ERole
        LocalDateTime createdAt
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(), // Convert ERole to String
                user.getCreatedAt()
        );
    }
}
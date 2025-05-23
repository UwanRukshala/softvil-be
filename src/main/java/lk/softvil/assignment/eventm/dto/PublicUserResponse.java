package lk.softvil.assignment.eventm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lk.softvil.assignment.eventm.model.entity.User;

import java.util.UUID;

public record PublicUserResponse(
        UUID id,
        String name,
        String contactEmail
) {
    public static PublicUserResponse fromEntity(User user, boolean isHost) {
        return new PublicUserResponse(
                user.getId(),
                user.getName(),
                isHost ? user.getEmail() : null
        );
    }
}
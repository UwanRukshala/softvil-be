package lk.softvil.assignment.eventm.dto;

import jakarta.validation.constraints.*;
import lk.softvil.assignment.eventm.model.enums.UserRole;

public record UserRegistrationRequest(
        @NotBlank String username,
        @Email String email,
        @NotNull UserRole role
) {}
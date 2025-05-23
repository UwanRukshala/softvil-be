package lk.softvil.assignment.eventm.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.softvil.assignment.eventm.model.enums.Visibility;

import java.time.LocalDateTime;

public record CreateEventRequest(
        @NotBlank String title,
        String description,
        @Future LocalDateTime startTime,
        @Future LocalDateTime endTime,
        String location,
        @NotNull Visibility visibility
) {}
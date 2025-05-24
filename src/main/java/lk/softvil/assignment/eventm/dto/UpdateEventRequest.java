package lk.softvil.assignment.eventm.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.softvil.assignment.eventm.model.enums.Visibility;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateEventRequest(
        @Size(min = 3, max = 100, message = "Title must be between 3-100 characters")
        String title,

        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        String description,

        @Future(message = "Start time must be in the future")
        LocalDateTime startTime,

        @Future(message = "End time must be in the future")
        LocalDateTime endTime,

        @Size(max = 100, message = "Location cannot exceed 100 characters")
        String location,

        Visibility visibility,

        @NotNull UUID hostId
) {
    // Optional: Add validation method for business rules
    public boolean isValidTimeRange() {
        return endTime.isAfter(startTime);
    }
}
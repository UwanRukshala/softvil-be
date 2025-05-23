package lk.softvil.assignment.eventm.dto;
import lk.softvil.assignment.eventm.model.entity.Event;
import lk.softvil.assignment.eventm.model.enums.Visibility;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventStatusResponse(
        UUID eventId,
        String title,
        String description,
        UUID hostId,

        // Timing Information
        LocalDateTime startTime,
        LocalDateTime endTime,

        boolean isOngoing,
        boolean isUpcoming,
        boolean isPast,

        // Event Metadata
        String location,
        Visibility visibility,

        // Timestamps
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static EventStatusResponse fromEvent(Event event, int attendeeCount) {
        LocalDateTime now = LocalDateTime.now();
        boolean isOngoing = now.isAfter(event.getStartTime()) && now.isBefore(event.getEndTime());
        boolean isUpcoming = now.isBefore(event.getStartTime());
        boolean isPast = now.isAfter(event.getEndTime());

        return new EventStatusResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getHost().getId(),
                event.getStartTime(),
                event.getEndTime(),
                isOngoing,
                isUpcoming,
                isPast,
                event.getLocation(),
                event.getVisibility(),
                event.getCreatedAt(),
                event.getUpdatedAt()
        );
    }
}
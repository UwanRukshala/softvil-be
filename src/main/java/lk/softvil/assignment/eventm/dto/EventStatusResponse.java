package lk.softvil.assignment.eventm.dto;
import lk.softvil.assignment.eventm.model.entity.Event;
import lk.softvil.assignment.eventm.model.enums.Visibility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime startTime = event.getStartTime();
        LocalDateTime endTime = event.getEndTime();
        LocalDateTime now = LocalDateTime.now();

        boolean isOngoing = now.isAfter(startTime) && now.isBefore(endTime);
        boolean isUpcoming = now.isBefore(startTime);
        boolean isPast = now.isAfter(endTime);

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
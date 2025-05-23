package lk.softvil.assignment.eventm.dto;

import lk.softvil.assignment.eventm.model.enums.AttendanceStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        String name,
        String email,
        String role,
        LocalDateTime createdAt,

        // Hosted events (summary)
        List<EventSummary> hostedEvents,

        // Attending events (summary)
        List<AttendingEvent> attendingEvents,

        // Statistics
        long totalHostedEvents,
        long totalAttendingEvents
) {
    public static record EventSummary(
            UUID id,
            String title,
            LocalDateTime startTime
    ) {}

    public static record AttendingEvent(
            UUID eventId,
            String eventTitle,
            AttendanceStatus status,
            LocalDateTime respondedAt
    ) {}
}
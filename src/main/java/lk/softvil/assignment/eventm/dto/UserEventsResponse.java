package lk.softvil.assignment.eventm.dto;

import lk.softvil.assignment.eventm.model.enums.AttendanceStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserEventsResponse(
        UUID userId,
        String userName,

        // Hosted Events
        List<HostedEvent> hostedEvents,

        // Attending Events
        List<AttendingEvent> attendingEvents,

        // Statistics
        int totalHostedEvents,
        int totalAttendingEvents
) {
    public record HostedEvent(
            UUID eventId,
            String title,
            LocalDateTime startTime,
            long attendeeCount,
            boolean isCancelled
    ) {}

    public record AttendingEvent(
            UUID eventId,
            String title,
            LocalDateTime startTime,
            AttendanceStatus status,
            String hostName
    ) {}
}
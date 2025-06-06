package lk.softvil.assignment.eventm.dto;

import lk.softvil.assignment.eventm.model.enums.Visibility;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record EventDetailsResponse(
        UUID eventId,
        String title,
        String description,

        String hostName,
        // Event Timing
        LocalDateTime startTime,
        LocalDateTime endTime,

        // Location Details
        String location,
        String locationDetails,

        // Attendance Info
        long attendeeCount,
        long maybeAttendeeCount,
        List<AttendeeInfo> attendees,

        // Visibility & Status
        Visibility visibility,
        boolean isCancelled,

        // Timestamps
        LocalDateTime createdAt,
        LocalDateTime updatedAt


) {
    public record AttendeeInfo(
            UUID userId,
            String userName,
            String avatarUrl,
            LocalDateTime respondedAt
    ) {}
}
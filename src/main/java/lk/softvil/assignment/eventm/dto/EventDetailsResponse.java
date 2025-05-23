package lk.softvil.assignment.eventm.dto;

import lk.softvil.assignment.eventm.model.enums.Visibility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EventDetailsResponse(
        UUID eventId,
        String title,
        String description,

        // Host Information
        UUID hostId,
        String hostName,
        String hostEmail,

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
        LocalDateTime updatedAt,

        // Additional Metadata
        List<String> tags,
        String imageUrl
) {
    public record AttendeeInfo(
            UUID userId,
            String userName,
            String avatarUrl,
            LocalDateTime respondedAt
    ) {}
}
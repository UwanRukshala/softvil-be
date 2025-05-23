package lk.softvil.assignment.eventm.dto;

import lk.softvil.assignment.eventm.model.enums.AttendanceStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventAttendanceResponse(
        UUID eventId,
        String eventTitle,
        LocalDateTime eventTime,
        AttendanceStatus status
) {}
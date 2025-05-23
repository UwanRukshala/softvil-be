package lk.softvil.assignment.eventm.dto;

import lk.softvil.assignment.eventm.model.enums.AttendanceStatus;

import java.util.Date;
import java.util.UUID;

public record AttendanceResponse(
        UUID id,
        UUID eventId,
        String eventTitle,
        UUID userId,
        String userName,
        AttendanceStatus status,
        Date respondedAt
) {}
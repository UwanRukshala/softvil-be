package lk.softvil.assignment.eventm.dto;

import lk.softvil.assignment.eventm.model.enums.AttendanceStatus;

import java.util.Map;
import java.util.UUID;

public record AttendanceStatsResponse(
        UUID eventId,
        Map<AttendanceStatus, Long> statusCounts,
        long totalResponses
) {}
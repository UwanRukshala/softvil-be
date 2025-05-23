package lk.softvil.assignment.eventm.dto;

import jakarta.validation.constraints.NotNull;
import lk.softvil.assignment.eventm.model.enums.AttendanceStatus;

public record AttendanceUpdateRequest(
        @NotNull AttendanceStatus status
) {}
package lk.softvil.assignment.eventm.controller;

import lk.softvil.assignment.eventm.dto.AttendanceResponse;
import lk.softvil.assignment.eventm.dto.AttendanceStatsResponse;
import lk.softvil.assignment.eventm.dto.AttendanceUpdateRequest;
import lk.softvil.assignment.eventm.model.entity.User;
import lk.softvil.assignment.eventm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/events/{eventId}")
    public AttendanceResponse respondToEvent(
            @PathVariable UUID eventId,
            @RequestBody AttendanceUpdateRequest request,
            @AuthenticationPrincipal User user) {
        return attendanceService.respondToEvent(eventId, user.getId(), request);
    }
    @GetMapping("/events/{eventId}")
    public AttendanceResponse getAttendanceEventStatus(
            @PathVariable UUID eventId,
            @RequestBody AttendanceUpdateRequest request,
            @AuthenticationPrincipal User user) {
        return attendanceService.respondToEvent(eventId, user.getId(), request);
    }

}
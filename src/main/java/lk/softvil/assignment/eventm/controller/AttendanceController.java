package lk.softvil.assignment.eventm.controller;

import lk.softvil.assignment.eventm.dto.AttendanceResponse;
import lk.softvil.assignment.eventm.dto.AttendanceStatsResponse;
import lk.softvil.assignment.eventm.dto.AttendanceUpdateRequest;
import lk.softvil.assignment.eventm.dto.EventAttendanceResponse;
import lk.softvil.assignment.eventm.model.entity.User;
import lk.softvil.assignment.eventm.security.CustomUserDetails;
import lk.softvil.assignment.eventm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return attendanceService.respondToEvent(eventId, customUserDetails.getUserId(), request);
    }

    @GetMapping("/events/{eventId}")
    public AttendanceResponse getAttendanceEventStatus(
            @PathVariable UUID eventId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return attendanceService.getEventAttendanceByUser(eventId, customUserDetails.getUserId());
    }

    @GetMapping("/myevents")
    public Page<EventAttendanceResponse> getAttendanceEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        return attendanceService.getUserAttendances(customUserDetails.getUserId(), page, size);
    }

    @GetMapping("/myhevents")
    public Page<EventAttendanceResponse> getHostingAttendanceEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        return attendanceService.getUserAttendances(customUserDetails.getUserId(), page, size);
    }

}
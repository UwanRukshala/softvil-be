package lk.softvil.assignment.eventm.controller;

import jakarta.validation.Valid;
import lk.softvil.assignment.eventm.dto.*;
import lk.softvil.assignment.eventm.model.enums.Visibility;
import lk.softvil.assignment.eventm.security.CustomUserDetails;
import lk.softvil.assignment.eventm.service.AttendanceService;
import lk.softvil.assignment.eventm.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AttendanceService attendanceService;

    // Create event (authenticated users only)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(
            @RequestBody @Valid CreateEventRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return eventService.createEvent(request, customUserDetails.getUserId());
    }

    // Update event (host or admin only)
    @PutMapping("/{eventId}")
    public EventResponse updateEvent(
            @PathVariable UUID eventId,
            @RequestBody @Valid UpdateEventRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return eventService.updateEvent(eventId, request, customUserDetails.getUserId());
    }

    // Soft delete event (host or admin only)
    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(
            @PathVariable UUID eventId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        eventService.softDeleteEvent(eventId, customUserDetails.getUserId());
    }

    // List events with filtering
    @GetMapping
    public Page<EventResponse> getEvents(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Visibility visibility,
            @PageableDefault(sort = "startTime", direction = Sort.Direction.ASC) Pageable pageable) {
        return eventService.getEvents(from, to, location, visibility, pageable);
    }

    @GetMapping("/all")
    public Page<EventResponse> getAllEvents(
            @RequestParam(required = false) Visibility visibility,
            @PageableDefault(sort = "startTime", direction = Sort.Direction.ASC) Pageable pageable) {
        return eventService.getAllEvents(visibility, pageable);
    }

    // Get event details with attendee count
    @GetMapping("/search/{eventId}")
    public EventDetailsResponse getEventDetails(@PathVariable UUID eventId) {
        return eventService.getEventDetails(eventId);
    }

    // List user's events (hosting or attending)
//    @GetMapping("/user/{userId}")
//    public UserEventsResponse getUserEvents(
//            @PathVariable UUID userId,
//            @PageableDefault Pageable pageable) {
//        return eventService.getUserEvents(userId, pageable);
//    }
//
//    // Check event status
//    @GetMapping("/{eventId}/status")
//    public EventStatusResponse getEventStatus(@PathVariable UUID eventId) {
//        return eventService.getEventStatus(eventId);
//    }
}
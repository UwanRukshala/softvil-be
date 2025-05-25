package lk.softvil.assignment.eventm.controller;

import jakarta.validation.Valid;
import lk.softvil.assignment.eventm.dto.*;
import lk.softvil.assignment.eventm.model.enums.Visibility;
import lk.softvil.assignment.eventm.security.CustomUserDetails;
import lk.softvil.assignment.eventm.service.AttendanceService;
import lk.softvil.assignment.eventm.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // Create event (authenticated users only)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public EventResponse createEvent(
            @RequestBody @Valid CreateEventRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return eventService.createEvent(request, customUserDetails);
    }

    // Update event (host or admin only)
    @PutMapping("/{eventId}")
    @PreAuthorize("hasAnyRole('ADMIN','HOST')")
    public EventResponse updateEvent(
            @PathVariable UUID eventId,
            @RequestBody @Valid UpdateEventRequest request) {
        return eventService.updateEvent(eventId, request);
    }

    // Soft delete event (host or admin only)
    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','HOST')")
    public void deleteEvent(
            @PathVariable UUID eventId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        eventService.softDeleteEvent(eventId, customUserDetails.getUserId());
    }

    // List events with filtering
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HOST')")
    public Page<EventResponse> getEvents(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Visibility visibility,
            @PageableDefault(sort = "startTime", direction = Sort.Direction.ASC) Pageable pageable) {
        return eventService.getEvents(from, to, location, visibility, pageable);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','HOST')")
    public Page<EventResponse> getAllEvents(
            @RequestParam(required = false) Visibility visibility,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PageableDefault(sort = "startTime", direction = Sort.Direction.ASC) Pageable pageable) {
        return eventService.getAllEvents(visibility, pageable);
    }

    // Get event details with attendee count
    @GetMapping("/search/{eventId}")
    public EventDetailsResponse getEventDetails(@PathVariable UUID eventId) {
        return eventService.getEventDetails(eventId);
    }

    @GetMapping("/upcoming")
    @Cacheable(
            value = "upcomingEvents",
            key = "'user_' + #customUserDetails?.userId + " +
                    "'_visibility_' + (#visibility != null ? #visibility.name() : 'ALL') + " +
                    "'_location_' + (#location != null ? #location : 'any') + " +
                    "'_from_' + (#from != null ? #from.toString() : 'null') + " +
                    "'_to_' + (#to != null ? #to.toString() : 'null') + " +
                    "'_page_' + #pageable.pageNumber + '_size_' + #pageable.pageSize + " +
                    "'_sort_' + #pageable.sort.toString()"
    )
    public Page<EventResponse> getUpcomingEvents(
            @RequestParam(required = false) Visibility visibility,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @PageableDefault(sort = "startTime", direction = Sort.Direction.ASC) Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails customUserDetails

    ) {
        return eventService.getUpcomingEvents(
                visibility, location, from, to, pageable, customUserDetails.getUserId()
        );
    }

    @GetMapping("/myhosting")
    @PreAuthorize("hasAnyRole('ADMIN','HOST')")
    public Page<EventResponse> getMyHostingEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PageableDefault(sort = "startTime", direction = Sort.Direction.ASC) Pageable pageable) {
        return eventService.getHostingEventsByUser(customUserDetails.getUserId(), pageable);
    }
}
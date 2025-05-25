package lk.softvil.assignment.eventm.service;

import lk.softvil.assignment.eventm.dto.*;
import lk.softvil.assignment.eventm.model.enums.Visibility;
import lk.softvil.assignment.eventm.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.UUID;

public interface EventService {
    EventResponse createEvent(CreateEventRequest request, CustomUserDetails customUserDetails);
    EventResponse updateEvent(UUID eventId, UpdateEventRequest request);
    void softDeleteEvent(UUID eventId, UUID userId);
    Page<EventResponse> getEvents(LocalDateTime from, LocalDateTime to,
                                  String location, Visibility visibility,
                                  Pageable pageable);
    Page<EventResponse> getAllEvents(Visibility visibility,Pageable pageable);
    EventDetailsResponse getEventDetails(UUID eventId);
    Page<EventResponse>  getHostingEventsByUser(UUID userId,Pageable pageable);
    Page<EventResponse> getUpcomingEvents(Visibility visibility, Pageable pageable,UUID userId);
}
package lk.softvil.assignment.eventm.service;

import lk.softvil.assignment.eventm.dto.*;
import lk.softvil.assignment.eventm.model.enums.Visibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.UUID;

public interface EventService {
    EventResponse createEvent(CreateEventRequest request, UUID hostId);
    EventResponse updateEvent(UUID eventId, UpdateEventRequest request, UUID userId);
    void softDeleteEvent(UUID eventId, UUID userId);
    Page<EventResponse> getEvents(LocalDateTime from, LocalDateTime to,
                                  String location, Visibility visibility,
                                  Pageable pageable);
    Page<EventResponse> getAllEvents(Visibility visibility,Pageable pageable);
    EventDetailsResponse getEventDetails(UUID eventId);
}
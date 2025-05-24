package lk.softvil.assignment.eventm.service.impl;

import lk.softvil.assignment.eventm.dto.*;
import lk.softvil.assignment.eventm.exception.AccessDeniedException;
import lk.softvil.assignment.eventm.exception.ResourceNotFoundException;
import lk.softvil.assignment.eventm.mapper.EventMapper;
import lk.softvil.assignment.eventm.model.entity.Event;
import lk.softvil.assignment.eventm.model.entity.User;
import lk.softvil.assignment.eventm.model.enums.Visibility;
import lk.softvil.assignment.eventm.repository.EventRepository;
import lk.softvil.assignment.eventm.repository.UserRepository;
import lk.softvil.assignment.eventm.security.CustomUserDetails;
import lk.softvil.assignment.eventm.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Transactional
@CacheConfig(cacheNames = "events")
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Override
    public EventResponse createEvent(CreateEventRequest request, CustomUserDetails customUserDetails) {
        User host = userRepository.findById(request.hostId())
                .orElseThrow(() -> new ResourceNotFoundException("Host not found with id: " + request.hostId()));

        if (!isUserHostOrAdmin(customUserDetails.getUserId())) {
            throw new AccessDeniedException("Only users with HOST role can create events");
        }
        Event event = Event.builder()
                .title(request.title())
                .description(request.description())
                .host(host)
                .startTime(request.startTime())
                .endTime(request.endTime())
                .location(request.location())
                .visibility(request.visibility())
                .deleted(false)
                .build();

        Event savedEvent = eventRepository.save(event);
        return eventMapper.toEventResponse(savedEvent);
    }

    @Override
    public EventResponse updateEvent(UUID eventId, UpdateEventRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        eventMapper.updateEventFromRequest(request, event);

        if (request.hostId() != null) {
            User newHost = userRepository.findById(request.hostId())
                    .orElseThrow(() -> new ResourceNotFoundException("Host not found"));
//            if (!isUserHost(newHost)) {
//                throw new AccessDeniedException("Only hosts can be assigned as event hosts");
//            }
            event.setHost(newHost);
        }

        if (request.title() != null) {
            event.setTitle(request.title());
        }
        if (request.description() != null) {
            event.setDescription(request.description());
        }
        if (request.startTime() != null) {
            event.setStartTime(request.startTime());
        }
        if (request.endTime() != null) {
            event.setEndTime(request.endTime());
        }
        if (request.location() != null) {
            event.setLocation(request.location());
        }
        if (request.visibility() != null) {
            event.setVisibility(request.visibility());
        }
        if (request.hostId() != null) {
            User newHost = userRepository.findById(request.hostId())
                    .orElseThrow(() -> new ResourceNotFoundException("Host not found with id: " + request.hostId()));

//            if (!isUserHost(newHost)) {
//                throw new AccessDeniedException("Only users with HOST role can be assigned as event hosts");
//            }
            event.setHost(newHost);
        }

        Event updatedEvent = eventRepository.save(event);
        return eventMapper.toEventResponse(updatedEvent);
    }

    @Override
    public void softDeleteEvent(UUID eventId, UUID userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        event.softDelete();
        eventRepository.save(event);
    }

    @Override
    public Page<EventResponse> getEvents(LocalDateTime from, LocalDateTime to,
                                         String location, Visibility visibility,
                                         Pageable pageable) {
        Specification<Event> spec = Specification.where(notDeleted())
                .and(from != null ? startTimeAfter(from) : null)
                .and(to != null ? endTimeBefore(to) : null)
                .and(location != null ? locationContains(location) : null)
                .and(visibility != null ? visibilityEquals(visibility) : null);

        return eventMapper.mapEventPageToEventResponsePage(
                eventRepository.findAll(spec, pageable)
        );
    }

    @Override
    public Page<EventResponse> getAllEvents(Visibility visibility, Pageable pageable) {
        Specification<Event> spec = Specification.where(notDeleted())
                .and(visibility != null && visibility != Visibility.ALL ? visibilityEquals(visibility) : null);

        return eventMapper.mapEventPageToEventResponsePage(
                eventRepository.findAll(spec, pageable)
        );
    }

    @Override
    public EventDetailsResponse getEventDetails(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        return EventDetailsResponse.builder()
                .eventId(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .location(event.getLocation())
                .visibility(event.getVisibility())
                .hostId(event.getHost().getId())
                .hostName(event.getHost().getName())
                .hostEmail(event.getHost().getEmail())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }

    // Helper methods
    private boolean isUserHost(UUID userId) {

        User user = userRepository.findById(userId).get();
        return "HOST".equals(user.getRole().name()) || user.isBothAdminAndHost();
    }

    private boolean isUserHostOrAdmin(UUID userId) {

        User user = userRepository.findById(userId).get();
        return "HOST".equals(user.getRole().name()) || "ADMIN".equals(user.getRole().name()) || user.isBothAdminAndHost();
    }

    // Specification methods for filtering
    private static Specification<Event> notDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }

    private static Specification<Event> startTimeAfter(LocalDateTime from) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("startTime"), from.toString());
    }

    private static Specification<Event> endTimeBefore(LocalDateTime to) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("endTime"), to.toString());
    }

    private static Specification<Event> locationContains(String location) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }

    private static Specification<Event> visibilityEquals(Visibility visibility) {
        return (root, query, cb) -> cb.equal(root.get("visibility"), visibility);
    }
}
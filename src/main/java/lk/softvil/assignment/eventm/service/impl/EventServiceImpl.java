package lk.softvil.assignment.eventm.service.impl;

import lk.softvil.assignment.eventm.dto.*;
import lk.softvil.assignment.eventm.model.enums.Visibility;
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
    @Override
    public EventResponse createEvent(CreateEventRequest request, UUID hostId) {
        return null;
    }

    @Override
    public EventResponse updateEvent(UUID eventId, UpdateEventRequest request, UUID userId) {
        return null;
    }

    @Override
    public void softDeleteEvent(UUID eventId, UUID userId) {

    }

    @Override
    public Page<EventResponse> getEvents(LocalDateTime from, LocalDateTime to, String location, Visibility visibility, Pageable pageable) {
        return null;
    }

    @Override
    public Page<EventResponse> getAllEvents(Visibility visibility, Pageable pageable) {
        return null;
    }

    @Override
    public EventDetailsResponse getEventDetails(UUID eventId) {
        return null;
    }
//
//    private final EventRepository eventRepository;
//    private final AttendanceRepository attendanceRepository;
//    private final UserRepository userRepository;
//    private final EventMapper eventMapper;
//
//    @Override
//    @CacheEvict(allEntries = true)
//    public EventResponse createEvent(CreateEventRequest request, UUID hostId) {
//        User host = userRepository.findById(hostId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        Event event = Event.builder()
//                .title(request.title())
//                .description(request.description())
//                .host(host)
//                .startTime(request.startTime())
//                .endTime(request.endTime())
//                .location(request.location())
//                .visibility(request.visibility())
//                .build();
//
//        event = eventRepository.save(event);
//        return eventMapper.toResponse(event);
//    }
//
//    @Override
//    @CachePut(key = "#eventId")
//    public EventResponse updateEvent(UUID eventId, UpdateEventRequest request, UUID userId) {
//        Event event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
//
//        if (!event.getHost().getId().equals(userId)) {
//            throw new AccessDeniedException("Only event host can update the event");
//        }
//
//        event.updateDetails(
//                request.title(),
//                request.description(),
//                request.startTime(),
//                request.endTime(),
//                request.location(),
//                request.visibility()
//        );
//
//        return eventMapper.toResponse(eventRepository.save(event));
//    }
//
//    @Override
//    @CacheEvict(key = "#eventId")
//    public void softDeleteEvent(UUID eventId, UUID userId) {
//        Event event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
//
//        if (!event.getHost().getId().equals(userId)) {
//            throw new AccessDeniedException("Only event host can delete the event");
//        }
//
//        event.softDelete();
//        eventRepository.save(event);
//    }
//
//    @Override
//    @Cacheable
//    public Page<EventResponse> getEvents(
//            LocalDateTime from,
//            LocalDateTime to,
//            String location,
//            Visibility visibility,
//            Pageable pageable) {
//
//        Specification<Event> spec = Specification.where(null);
//
//        if (from != null && to != null) {
//            spec = spec.and(EventSpecifications.betweenDates(from, to));
//        }
//
//        if (location != null) {
//            spec = spec.and(EventSpecifications.byLocation(location));
//        }
//
//        if (visibility != null) {
//            spec = spec.and(EventSpecifications.byVisibility(visibility));
//        }
//
//        return eventRepository.findAll(spec, pageable)
//                .map(eventMapper::toResponse);
//    }
//
//    @Override
//    @Cacheable(key = "#eventId")
//    public EventDetailsResponse getEventDetails(UUID eventId) {
//        Event event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
//
//        long attendeeCount = attendanceRepository.countByEventId(eventId);
//
//        return eventMapper.toDetailsResponse(event, attendeeCount);
//    }
}
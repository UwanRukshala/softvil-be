package lk.softvil.assignment.eventm.service.impl;

import lk.softvil.assignment.eventm.dto.AttendanceResponse;
import lk.softvil.assignment.eventm.dto.AttendanceUpdateRequest;
import lk.softvil.assignment.eventm.exception.ResourceNotFoundException;
import lk.softvil.assignment.eventm.mapper.AttendanceMapper;
import lk.softvil.assignment.eventm.model.entity.Event;
import lk.softvil.assignment.eventm.model.entity.User;
import lk.softvil.assignment.eventm.model.enums.Visibility;
import lk.softvil.assignment.eventm.repository.AttendanceRepository;
import lk.softvil.assignment.eventm.repository.EventRepository;
import lk.softvil.assignment.eventm.repository.UserRepository;
import lk.softvil.assignment.eventm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    public AttendanceResponse respondToEvent(UUID eventId, UUID userId, AttendanceUpdateRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Check if event is private and user is invited (optional enhancement)
        if (event.getVisibility() == Visibility.PRIVATE && !isUserInvited(event, userId)) {
            throw new AccessDeniedException("You are not invited to this private event");
        }

        Attendance attendance = attendanceRepository.findByEventIdAndUserId(eventId, userId)
                .orElseGet(() -> {
                    Attendance newAttendance = new Attendance();
                    newAttendance.setEvent(event);
                    newAttendance.setUser(user);
                    return newAttendance;
                });

        attendance.setStatus(request.status());
        attendance.setRespondedAt(new Date());

        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }

    @Override
    public Page<AttendanceResponse> getEventAttendees(UUID eventId, Pageable pageable) {
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event not found with id: " + eventId);
        }
        return attendanceRepository.findByEventId(eventId, pageable)
                .map(attendanceMapper::toResponse);
    }

    @Override
    public Page<EventAttendanceResponse> getUserAttendances(UUID userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return attendanceRepository.findByUserId(userId, pageable)
                .map(attendance -> attendanceMapper.toEventAttendanceResponse(
                        attendance.getEvent(),
                        attendance.getStatus()
                ));
    }

    @Override
    public long getAttendeeCount(UUID eventId) {
        return attendanceRepository.countByEventIdAndStatus(eventId, AttendanceStatus.GOING);
    }

    @Override
    public AttendanceStatsResponse getEventAttendanceStats(UUID eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event not found with id: " + eventId);
        }

        Map<AttendanceStatus, Long> statusCounts = new EnumMap<>(AttendanceStatus.class);
        for (AttendanceStatus status : AttendanceStatus.values()) {
            statusCounts.put(status,
                    attendanceRepository.countByEventIdAndStatus(eventId, status));
        }

        return new AttendanceStatsResponse(
                eventId,
                statusCounts,
                attendanceRepository.countByEventId(eventId)
        );
    }

    @Override
    public void removeAttendance(UUID eventId, UUID userId) {
        attendanceRepository.deleteByEventIdAndUserId(eventId, userId);
    }

    private boolean isUserInvited(Event event, UUID userId) {
        // Implement your invitation logic here
        return false; // Placeholder
    }
}
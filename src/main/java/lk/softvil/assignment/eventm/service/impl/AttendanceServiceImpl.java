package lk.softvil.assignment.eventm.service.impl;

import lk.softvil.assignment.eventm.dto.AttendanceResponse;
import lk.softvil.assignment.eventm.dto.AttendanceUpdateRequest;
import lk.softvil.assignment.eventm.dto.EventAttendanceResponse;
import lk.softvil.assignment.eventm.exception.AccessDeniedException;
import lk.softvil.assignment.eventm.exception.ResourceNotFoundException;
import lk.softvil.assignment.eventm.mapper.AttendanceMapper;
import lk.softvil.assignment.eventm.model.entity.Attendance;
import lk.softvil.assignment.eventm.model.entity.Event;
import lk.softvil.assignment.eventm.model.entity.User;
import lk.softvil.assignment.eventm.model.enums.Visibility;
import lk.softvil.assignment.eventm.repository.AttendanceRepository;
import lk.softvil.assignment.eventm.repository.EventRepository;
import lk.softvil.assignment.eventm.repository.UserRepository;
import lk.softvil.assignment.eventm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

        // Check if event is private
        if (event.getVisibility() == Visibility.PRIVATE) {
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
        attendance.setRespondedAt(LocalDateTime.now());

        return attendanceMapper.toAttendanceResponse(attendanceRepository.save(attendance));
    }

    @Override
    public AttendanceResponse getEventAttendanceByUser(UUID eventId, UUID userId) {
        return attendanceRepository.findByEventIdAndUserId(eventId, userId)
                .map(attendanceMapper::toAttendanceResponse)
                .orElse(null);
    }


    @Override
    public Page<EventAttendanceResponse> getUserAttendances(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("respondedAt").descending()
        );

        Page<Attendance> attendances = attendanceRepository.findByUserId(userId, pageable);
        return attendances.map(attendanceMapper::toEventAttendanceResponse);
    }

}
package lk.softvil.assignment.eventm.service;


import lk.softvil.assignment.eventm.dto.AttendanceResponse;
import lk.softvil.assignment.eventm.dto.AttendanceUpdateRequest;
import lk.softvil.assignment.eventm.dto.EventAttendanceResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AttendanceService {
    AttendanceResponse respondToEvent(UUID eventId, UUID userId, AttendanceUpdateRequest request);
    AttendanceResponse getEventAttendanceByUser(UUID eventId, UUID userId);
    Page<EventAttendanceResponse> getUserAttendances(UUID userId,int page,int size);

}
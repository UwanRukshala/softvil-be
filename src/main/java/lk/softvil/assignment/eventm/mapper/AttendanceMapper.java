package lk.softvil.assignment.eventm.mapper;
import lk.softvil.assignment.eventm.dto.AttendanceResponse;
import lk.softvil.assignment.eventm.dto.AttendanceStatsResponse;
import lk.softvil.assignment.eventm.dto.EventAttendanceResponse;
import lk.softvil.assignment.eventm.model.entity.Attendance;
import lk.softvil.assignment.eventm.model.enums.AttendanceStatus;
import org.mapstruct.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    // Mapping Attendance ➝ AttendanceResponse
    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "event.title", target = "eventTitle")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "respondedAt", target = "respondedAt", qualifiedByName = "toDate")
    AttendanceResponse toAttendanceResponse(Attendance attendance);

    // Convert LocalDateTime ➝ java.util.Date
    @Named("toDate")
    static Date mapLocalDateTimeToDate(LocalDateTime respondedAt) {
        return respondedAt == null ? null :
                Date.from(respondedAt.atZone(ZoneId.systemDefault()).toInstant());
    }

    // Manually build AttendanceStatsResponse
    default AttendanceStatsResponse toAttendanceStatsResponse(UUID eventId, List<Attendance> attendances) {
        Map<AttendanceStatus, Long> statusCounts = attendances.stream()
                .collect(Collectors.groupingBy(
                        Attendance::getStatus,
                        Collectors.counting()
                ));

        long totalResponses = attendances.size();

        return new AttendanceStatsResponse(eventId, statusCounts, totalResponses);
    }

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "event.title", target = "eventTitle")
    @Mapping(source = "event.startTime", target = "eventTime")
    EventAttendanceResponse toEventAttendanceResponse(Attendance attendance);
}

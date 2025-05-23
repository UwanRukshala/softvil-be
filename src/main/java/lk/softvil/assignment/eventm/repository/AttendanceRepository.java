package lk.softvil.assignment.eventm.repository;

import lk.softvil.assignment.eventm.model.entity.Attendance;
import lk.softvil.assignment.eventm.model.enums.AttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {

    // Find attendance record for specific user and event
    Optional<Attendance> findByEventIdAndUserId(UUID eventId, UUID userId);

    // Count attendees for an event
    long countByEventId(UUID eventId);

    // Count attendees with specific status
    long countByEventIdAndStatus(UUID eventId, AttendanceStatus status);

    // Get all attendances for a user (paginated)
    Page<Attendance> findByUserId(UUID userId, Pageable pageable);

    // Get all attendances for an event (paginated)
    Page<Attendance> findByEventId(UUID eventId, Pageable pageable);

    // Update attendance status
    @Modifying
    @Query("UPDATE Attendance a SET a.status = :status WHERE a.id = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") AttendanceStatus status);

    // Check if user is attending an event
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Attendance a WHERE a.event.id = :eventId AND a.user.id = :userId")
    boolean existsByEventAndUser(@Param("eventId") UUID eventId, @Param("userId") UUID userId);

    // Find all events a user is attending with specific status
    @Query("SELECT a.event.id FROM Attendance a " +
            "WHERE a.user.id = :userId AND a.status = :status")
    List<UUID> findEventIdsByUserAndStatus(
            @Param("userId") UUID userId,
            @Param("status") AttendanceStatus status
    );
}
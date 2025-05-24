package lk.softvil.assignment.eventm.model.entity;

import jakarta.persistence.*;
import lk.softvil.assignment.eventm.model.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "attendance")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Column(name = "responded_at", nullable = false)
    private LocalDateTime respondedAt;

    // Getters and setters
    @PrePersist
    protected void onRespond() {
        respondedAt = LocalDateTime.now();
    }
}


package lk.softvil.assignment.eventm.model.entity;

import jakarta.persistence.*;
import lk.softvil.assignment.eventm.model.enums.AttendanceStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "eventId", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status; // Enum: GOING, MAYBE

    @Column(name = "respondedAt", nullable = false)
    private LocalDateTime respondedAt;

    // Getters and setters
    @PrePersist
    protected void onRespond() {
        respondedAt = LocalDateTime.now();
    }
}


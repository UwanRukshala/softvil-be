package lk.softvil.assignment.eventm.model.entity;

import jakarta.persistence.*;
import lk.softvil.assignment.eventm.model.enums.Visibility;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
public class Event {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "hostId", referencedColumnName = "id")
    private User host; // Foreign key to User

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String location;

    @Enumerated(EnumType.STRING)
    private Visibility visibility; // Enum: PUBLIC, PRIVATE

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean deleted;

    public void softDelete() {
        this.deleted = true;
    }

    // Getters, setters, and timestamp handlers
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}


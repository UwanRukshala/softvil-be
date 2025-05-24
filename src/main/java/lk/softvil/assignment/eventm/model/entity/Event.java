package lk.softvil.assignment.eventm.model.entity;

import jakarta.persistence.*;
import lk.softvil.assignment.eventm.model.enums.Visibility;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private User host; // Foreign key to User

    private String startTime;

    private String endTime;

    private String location;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
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


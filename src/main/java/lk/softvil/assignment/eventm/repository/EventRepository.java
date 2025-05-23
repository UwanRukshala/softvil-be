package lk.softvil.assignment.eventm.repository;

import lk.softvil.assignment.eventm.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
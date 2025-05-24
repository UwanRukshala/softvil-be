package lk.softvil.assignment.eventm.repository;

import lk.softvil.assignment.eventm.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.UUID;

public interface EventRepository extends
        JpaRepository<Event, UUID>,
        JpaSpecificationExecutor<Event> {


}
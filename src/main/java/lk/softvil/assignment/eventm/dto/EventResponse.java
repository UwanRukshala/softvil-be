package lk.softvil.assignment.eventm.dto;

import lk.softvil.assignment.eventm.model.enums.Visibility;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponse (
    UUID id,
    String title,
    String description,
    UUID hostId,
    String hostName,
    LocalDateTime startTime,
    LocalDateTime endTime,
    String location,
    Visibility visibility,
    long attendeeCount
){}

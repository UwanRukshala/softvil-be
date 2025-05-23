package lk.softvil.assignment.eventm.mapper;

import lk.softvil.assignment.eventm.dto.EventDetailsResponse;
import lk.softvil.assignment.eventm.dto.EventResponse;
import lk.softvil.assignment.eventm.model.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "hostId", source = "host.id")
    @Mapping(target = "hostName", source = "host.name")
    EventResponse toResponse(Event event);

    @Mapping(target = "hostId", source = "host.id")
    @Mapping(target = "hostName", source = "host.name")
    EventDetailsResponse toDetailsResponse(Event event, long attendeeCount);

}
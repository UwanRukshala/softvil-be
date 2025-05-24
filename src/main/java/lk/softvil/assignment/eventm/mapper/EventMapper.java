package lk.softvil.assignment.eventm.mapper;

import lk.softvil.assignment.eventm.dto.CreateEventRequest;
import lk.softvil.assignment.eventm.dto.EventDetailsResponse;
import lk.softvil.assignment.eventm.dto.EventResponse;
import lk.softvil.assignment.eventm.dto.UpdateEventRequest;
import lk.softvil.assignment.eventm.model.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;


@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "startTime", expression = "java(event.getStartTime())")
    @Mapping(target = "endTime", expression = "java(event.getEndTime())")
    @Mapping(target = "hostId", source = "host.id")
    @Mapping(target = "hostName", source = "host.name")
    EventResponse toEventResponse(Event event);

    @Mapping(target = "startTime", expression = "java(request.startTime())")
    @Mapping(target = "endTime", expression = "java(request.endTime())")
    Event toEvent(CreateEventRequest request);

    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "host", ignore = true)
    void updateEventFromRequest(UpdateEventRequest request, @MappingTarget Event event);

    default Page<EventResponse> mapEventPageToEventResponsePage(Page<Event> eventPage) {
        return eventPage.map(this::toEventResponse);
    }

}
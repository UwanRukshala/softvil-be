package lk.softvil.assignment.eventm.mapper;

import lk.softvil.assignment.eventm.dto.EventResponse;
import lk.softvil.assignment.eventm.dto.UserProfileResponse;
import lk.softvil.assignment.eventm.dto.UserResponse;
import lk.softvil.assignment.eventm.model.entity.User;
import lk.softvil.assignment.eventm.model.enums.UserRole;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "role.name") // Maps ERole enum to String
    UserResponse toResponse(User user);

    @Mapping(target = "createdEvents", ignore = true) // Will be set manually
    @Mapping(target = "attendedEvents", ignore = true)
    UserProfileResponse toProfileResponse(User user,
                                          @Context List<EventResponse> createdEvents,
                                          @Context List<EventResponse> attendedEvents);

    default String mapERoleToString(UserRole role) {
        return role != null ? role.name() : null;
    }
}
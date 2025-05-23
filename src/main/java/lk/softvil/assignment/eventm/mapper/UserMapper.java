package lk.softvil.assignment.eventm.mapper;

import lk.softvil.assignment.eventm.dto.EventResponse;
import lk.softvil.assignment.eventm.dto.UserProfileResponse;
import lk.softvil.assignment.eventm.dto.UserResponse;
import lk.softvil.assignment.eventm.model.entity.User;
import lk.softvil.assignment.eventm.model.enums.UserRole;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(target = "role", source = "role", qualifiedByName = "roleToString")
//    UserResponse toResponse(User user);
//
//    @Mapping(target = "createdEvents", expression = "java(createdEvents)")
//    @Mapping(target = "attendedEvents", expression = "java(attendedEvents)")
//    UserProfileResponse toProfileResponse(
//            User user,
//            @Context List<EventResponse> createdEvents,
//            @Context List<EventResponse> attendedEvents
//    );
//
//    @Named("roleToString")
//    default String roleToString(UserRole role) {
//        return role != null ? role.name() : null;
//    }
}
package lk.softvil.assignment.eventm.repository.specification;

import lk.softvil.assignment.eventm.model.entity.Event;

import java.time.LocalDateTime;

public class EventSpecifications {

//    public static Specification<Event> betweenDates(LocalDateTime from, LocalDateTime to) {
//        return (root, query, cb) -> cb.and(
//                cb.greaterThanOrEqualTo(root.get("startTime"), from),
//                cb.lessThanOrEqualTo(root.get("endTime"), to)
//        );
//    }
//
//    public static Specification<Event> byLocation(String location) {
//        return (root, query, cb) -> cb.equal(
//                cb.lower(root.get("location")),
//                location.toLowerCase()
//        );
//    }
}
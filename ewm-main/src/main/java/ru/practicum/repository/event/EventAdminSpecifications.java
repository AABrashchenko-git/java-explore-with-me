package ru.practicum.repository.event;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.model.entity.Event;
import ru.practicum.enums.EventState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventAdminSpecifications {

    public static Specification<Event> withUsers(List<Long> users) {
        return (root, query, cb) -> {
            if (users == null || users.isEmpty())
                return null;
            return root.get("initiator").get("id").in(users);
        };
    }

    public static Specification<Event> withStates(List<String> states) {
        return (root, query, cb) -> {
            if (states == null || states.isEmpty())
                return null;
            List<EventState> eventStates = new ArrayList<>();
            for (String state : states) {
                try {
                    eventStates.add(EventState.valueOf(state.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid EventState value: " + state);
                }
            }
            return root.get("state").in(eventStates);
        };
    }

    public static Specification<Event> withCategories(List<Long> categories) {
        return (root, query, cb) -> {
            if (categories == null || categories.isEmpty())
                return null;
            return root.get("category").get("id").in(categories);
        };
    }

    public static Specification<Event> withRangeStart(LocalDateTime rangeStart) {
        return (root, query, cb) -> rangeStart == null ? null : cb.greaterThanOrEqualTo(root.get("eventDate"), rangeStart);
    }

    public static Specification<Event> withRangeEnd(LocalDateTime rangeEnd) {
        return (root, query, cb) -> rangeEnd == null ? null : cb.lessThanOrEqualTo(root.get("eventDate"), rangeEnd);
    }
}

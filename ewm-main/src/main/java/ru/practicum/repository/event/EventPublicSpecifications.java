package ru.practicum.repository.event;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.enums.EventState;
import ru.practicum.model.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public class EventPublicSpecifications {
    public static Specification<Event> withText(String text) {
        return (root, query, cb) -> {
            if (text == null || text.isBlank()) {
                return null;
            }
            String searchText = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("annotation")), searchText),
                    cb.like(cb.lower(root.get("description")), searchText)
            );
        };
    }

    public static Specification<Event> withCategories(List<Long> categories) {
        return (root, query, cb) -> {
            if (categories == null || categories.isEmpty()) {
                return null;
            }
            return root.get("category").get("id").in(categories);
        };
    }

    public static Specification<Event> withPaid(Boolean paid) {
        return (root, query, cb) -> paid == null ? null : cb.equal(root.get("paid"), paid);
    }

    public static Specification<Event> withRangeStart(LocalDateTime rangeStart) {
        return (root, query, cb) -> rangeStart == null ? null : cb.greaterThanOrEqualTo(root.get("eventDate"), rangeStart);
    }

    public static Specification<Event> withRangeEnd(LocalDateTime rangeEnd) {
        return (root, query, cb) -> rangeEnd == null ? null : cb.lessThanOrEqualTo(root.get("eventDate"), rangeEnd);
    }

    public static Specification<Event> withOnlyAvailable(Boolean onlyAvailable) {
        return (root, query, cb) -> {
            if (onlyAvailable == null || !onlyAvailable) {
                return null;
            }
            return cb.lessThan(root.get("confirmedRequests"), root.get("participantLimit"));
        };
    }

    public static Specification<Event> withPublishedState() {
        return (root, query, cb) -> cb.equal(root.get("state"), EventState.PUBLISHED);
    }
}
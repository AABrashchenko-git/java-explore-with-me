package ru.practicum.repository.location;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.model.entity.Event;

import java.util.List;

public class LocationSpecifications {
    //(String name,
    // Boolean available,
    // Double lat, Double lon, Double radius,
    //  Integer from, Integer size)

    public static Specification<Event> withName(String text) {
        return (root, query, cb) -> {
            if (text == null || text.isBlank()) {
                return null;
            }
            String searchText = "%" + text.toLowerCase() + "%";
            return cb.or(cb.like(cb.lower(root.get("name")), searchText));
        };
    }

    public static Specification<Event> withOnlyAvailable(Boolean onlyAvailable) {
        return (root, query, cb) -> {
            if (onlyAvailable == null || !onlyAvailable) {
                return null;
            }
            return cb.equal(root.get("available"), onlyAvailable);
        };
    }

}

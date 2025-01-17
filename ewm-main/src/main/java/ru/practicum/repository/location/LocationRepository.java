package ru.practicum.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.model.entity.Event;
import ru.practicum.model.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {
}

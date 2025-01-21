package ru.practicum.repository.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Page<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    @EntityGraph(attributePaths = {"category", "initiator", "location"})
    Optional<Event> findEventByIdAndInitiatorId(Long eventId, Long initiatorId);

    List<Event> findAllByIdIn(List<Long> eventsIds);

    boolean existsByCategoryId(Long catId);

    @Query(value = "SELECT * FROM events e WHERE distance(:lat, :lon, e.lat, e.lon) <= :radius AND e.state = 'PUBLISHED' " +
            "AND (e.participantLimit = 0 OR e.participantLimit > e.confirmedRequests)", nativeQuery = true)
    Page<Event> findEventsByCoordinates(@Param("lat") Double lat, @Param("lon") Double lon,
                                     @Param("radius") Double radius, Pageable pageable);

    @Query(value = "SELECT * FROM events e WHERE distance(:lat, :lon, e.lat, e.lon) <= :radius AND e.state = 'PUBLISHED'", nativeQuery = true)
    Page<Event> findAvailableEventsByCoordinates(@Param("lat") Double lat, @Param("lon") Double lon,
                                        @Param("radius") Double radius, Pageable pageable);

}

package ru.practicum.repository.location;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import ru.practicum.model.entity.Event;
import ru.practicum.model.entity.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {

    @Query("SELECT l FROM Location l JOIN FavoriteLocation f ON l.id = f.locationId WHERE f.userId = :userId")
    Page<Location> findFavoriteLocationsByUserId(@Param("userId") Long userId, Pageable pageable);

}

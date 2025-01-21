package ru.practicum.repository.location;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.entity.FavoriteLocation;

public interface FavoriteLocationRepository extends JpaRepository<FavoriteLocation, Long> {
    boolean existsByUserIdAndLocationId(Long userId, Long locationId);

    void deleteByUserIdAndLocationId(Long userId, Long locationId);


}

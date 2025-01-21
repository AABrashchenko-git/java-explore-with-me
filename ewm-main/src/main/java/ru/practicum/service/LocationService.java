package ru.practicum.service;

import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.model.dto.location.LocationDto;
import ru.practicum.model.dto.location.LocationFullDto;
import ru.practicum.model.dto.location.ShortLocationDto;
import ru.practicum.model.dto.location.UpdateLocationAdminRequest;

import java.util.List;

public interface LocationService {
    LocationFullDto addLocationByAdmin(LocationDto request);

    void deleteLocationByAdmin(Long locationId);

    LocationFullDto updateLocationByAdmin(Long locationId, UpdateLocationAdminRequest request);

    List<ShortLocationDto> findLocationsByAdmin(String name, Boolean available, Integer from, Integer size);

    LocationFullDto getLocationByIdPublic(Long id);

    List<ShortLocationDto> findLocationsPublic(String name, Integer from, Integer size);

    List<EventShortDto> getEventsByLocationIdPublic(Long id, Integer from, Integer size, Boolean onlyAvailable);

    List<ShortLocationDto> getFavoriteLocationsPrivate(Long userId, Integer from, Integer size);

    ShortLocationDto addFavoriteLocationPrivate(Long userId, Long locationId);

    void removeFavoriteLocationPrivate(Long userId, Long locationId);

    List<EventShortDto> getEventsByCoordinatesPublic(Double lat, Double lon, Double radius, Integer from, Integer size,
                                                     Boolean onlyAvailableEvents);
}

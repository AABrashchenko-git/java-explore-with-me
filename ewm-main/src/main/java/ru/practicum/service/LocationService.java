package ru.practicum.service;

import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.location.LocationDto;
import ru.practicum.model.dto.location.LocationFullDto;
import ru.practicum.model.dto.location.ShortLocationDto;
import ru.practicum.model.dto.location.UpdateLocationAdminRequest;

import java.util.List;

public interface LocationService {
    LocationFullDto addLocationByAdmin(LocationDto request);

    void deleteLocationByAdmin(Long locationId);

    LocationFullDto updateLocationByAdmin(Long locationId, UpdateLocationAdminRequest request);

    LocationFullDto getOneLocationByAdmin(Long locationId);

    List<LocationFullDto> getLocationsByAdmin(String name, Double lat, Double lon, Double radius,
                                              Boolean available, Integer from, Integer size);

    ShortLocationDto getLocationByIdPublic(Long id);

    List<ShortLocationDto> getAllLocationsPublic(String name, Double lat, Double lon, Double radius, Integer from, Integer size);

    List<EventFullDto> getEventsByLocationIdPrivate(Long id, Integer from, Integer size, Boolean onlyAvailable);

    List<ShortLocationDto> getFavoriteLocationsPrivate(Long userId, Integer from, Integer size);

    ShortLocationDto addFavoriteLocationPrivate(Long userId, Long locationId);

    ShortLocationDto removeFavoriteLocationPrivate(Long userId, Long locationId);
}

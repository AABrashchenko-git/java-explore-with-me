package ru.practicum.service;

import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.model.dto.location.LocationDto;
import ru.practicum.model.dto.location.LocationFullResponseDto;
import ru.practicum.model.dto.location.LocationShortResponseDto;
import ru.practicum.model.dto.location.UpdateLocationAdminRequest;

import java.util.List;

public interface LocationService {
    LocationFullResponseDto addLocationByAdmin(LocationDto request);

    void deleteLocationByAdmin(Long locationId);

    LocationFullResponseDto updateLocationByAdmin(Long locationId, UpdateLocationAdminRequest request);

    List<LocationShortResponseDto> findLocationsByAdmin(String name, Boolean available, Integer from, Integer size);

    LocationFullResponseDto getLocationByIdPublic(Long id);

    List<LocationShortResponseDto> findLocationsPublic(String name, Integer from, Integer size);

    List<EventShortDto> getEventsByLocationIdPublic(Long id, Integer from, Integer size, Boolean onlyAvailable);

    List<LocationShortResponseDto> getFavoriteLocationsPrivate(Long userId, Integer from, Integer size);

    LocationShortResponseDto addFavoriteLocationPrivate(Long userId, Long locationId);

    void removeFavoriteLocationPrivate(Long userId, Long locationId);

    List<EventShortDto> getEventsByCoordinatesPublic(Double lat, Double lon, Double radius, Integer from, Integer size,
                                                     Boolean onlyAvailableEvents);
}

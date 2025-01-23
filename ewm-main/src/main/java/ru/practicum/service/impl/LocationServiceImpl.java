package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.EventState;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.LocationMapper;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.model.dto.location.LocationFullResponseDto;
import ru.practicum.model.dto.location.LocationShortResponseDto;
import ru.practicum.model.dto.location.NewAdminLocationDto;
import ru.practicum.model.dto.location.UpdateLocationAdminRequest;
import ru.practicum.model.entity.Event;
import ru.practicum.model.entity.FavoriteLocation;
import ru.practicum.model.entity.Location;
import ru.practicum.repository.UserRepository;
import ru.practicum.repository.event.EventRepository;
import ru.practicum.repository.location.FavoriteLocationRepository;
import ru.practicum.repository.location.LocationRepository;
import ru.practicum.service.LocationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final FavoriteLocationRepository favoriteLocationRepository;
    private final UserRepository userRepository;
    private final LocationMapper locationMapper;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public LocationFullResponseDto addLocationByAdmin(NewAdminLocationDto request) {
        log.info("добавление локации админом: {}", request);
        Location location = locationMapper.adminDtoToLocation(request);
        Location savedLocation = locationRepository.save(location);
        return locationMapper.toLocationFullResponseDto(savedLocation);
    }

    @Override
    @Transactional
    public void deleteLocationByAdmin(Long locationId) {
        log.info("удааление локации админом, Id: {}", locationId);
        locationExistsCheck(locationId);
        locationRepository.deleteById(locationId);
    }

    @Override
    @Transactional
    public LocationFullResponseDto updateLocationByAdmin(Long locationId, UpdateLocationAdminRequest request) {
        log.info("обновление локации админом с id = {}, body {}", locationId, request);
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location not found with ID: " + locationId));

        if (request.getName() != null)
            location.setName(request.getName());
        if (request.getLat() != null)
            location.setLat(request.getLat());
        if (request.getLon() != null)
            location.setLon(request.getLon());
        if (request.getAvailable() != null)
            location.setAvailable(request.getAvailable());

        Location updatedLocation = locationRepository.save(location);
        return locationMapper.toLocationFullResponseDto(updatedLocation);
    }

    @Override
    public List<LocationShortResponseDto> findLocationsByAdmin(String name, Boolean onlyAvailable, Integer from, Integer size) {
        log.info("получение админом локаций с : name={}, available={}, from={}, size={}", name, onlyAvailable, from, size);

        Pageable pageable = PageRequest.of(from / size, size);
        Specification<Location> spec = Specification.where(null);

        if (name != null && !name.isBlank())
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        if (onlyAvailable)
            spec = spec.and((root, query, cb) -> cb.equal(root.get("available"), true));

        Page<Location> locations = locationRepository.findAll(spec, pageable);
        return locations.stream()
                .map(locationMapper::toLocationShortResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public LocationFullResponseDto getLocationByIdPublic(Long id) {
        log.info("получение локаций по id: {}", id);
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Location not found with ID: " + id));
        return locationMapper.toLocationFullResponseDto(location);
    }

    @Override
    public List<LocationShortResponseDto> findLocationsPublic(String name, Integer from, Integer size) {
        log.info("получение локаций public: name={}, from={}, size={}", name, from, size);

        Pageable pageable = PageRequest.of(from / size, size);
        Specification<Location> spec = Specification.where((root, query, cb) -> cb.isTrue(root.get("available")));

        if (name != null && !name.isBlank())
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));

        Page<Location> locations = locationRepository.findAll(spec, pageable);
        return locations.stream()
                .map(locationMapper::toLocationShortResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventShortDto> getEventsByLocationIdPublic(Long id, Integer from, Integer size, Boolean onlyAvailable) {
        log.info("получение событий для локации: {} с параметрами: from={}, size={}, onlyAvailable={}", id, from, size, onlyAvailable);

        locationExistsCheck(id);

        Pageable pageable = PageRequest.of(from / size, size);
        Specification<Event> spec = Specification.where((root, query, cb) -> cb.equal(root.get("location").get("id"), id));
        spec = spec.and((root, query, cb) -> cb.equal(root.get("state"), EventState.PUBLISHED));

        if (onlyAvailable != null && onlyAvailable)
            spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("participantLimit"), root.get("confirmedRequests")));

        Page<Event> events = eventRepository.findAll(spec, pageable);
        return events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventShortDto> getEventsByCoordinatesPublic(Double lat, Double lon, Double radius, Integer from,
                                                            Integer size, Boolean onlyAvailable) {
        Pageable pageable = PageRequest.of(from / size, size);
        Page<Event> events;
        if (onlyAvailable) {
            events = eventRepository.findAvailableEventsByCoordinates(lat, lon, radius, pageable);
        } else {
            events = eventRepository.findEventsByCoordinates(lat, lon, radius, pageable);
        }

        return events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LocationShortResponseDto addFavoriteLocationPrivate(Long userId, Long locationId) {
        log.info("добавление локации в избранное: {} для пользователя: {}", locationId, userId);
        userExistsCheck(userId);
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location not found with ID: " + locationId));
        if (favoriteLocationRepository.existsByUserIdAndLocationId(userId, locationId))
            throw new ConflictException("Location is already in favorites");

        FavoriteLocation favorite = FavoriteLocation.builder()
                .userId(userId)
                .locationId(locationId)
                .build();
        favoriteLocationRepository.save(favorite);
        return locationMapper.toLocationShortResponseDto(location);
    }

    @Override
    public List<LocationShortResponseDto> getFavoriteLocationsPrivate(Long userId, Integer from, Integer size) {
        log.info("получение избранных локаций для юзера с ID: {} с фильтрами: from={}, size={}", userId, from, size);
        userExistsCheck(userId);

        Pageable pageable = PageRequest.of(from / size, size);

        Page<Location> locations = locationRepository.findFavoriteLocationsByUserId(userId, pageable);
        return locations.stream()
                .map(locationMapper::toLocationShortResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeFavoriteLocationPrivate(Long userId, Long locationId) {
        log.info("удалене локации с ид: {} из избранного для пользователя ID: {}", locationId, userId);
        userExistsCheck(userId);
        locationExistsCheck(locationId);
        if (!favoriteLocationRepository.existsByUserIdAndLocationId(userId, locationId))
            throw new NotFoundException("Location not found in favorites");
        favoriteLocationRepository.deleteByUserIdAndLocationId(userId, locationId);
    }

    private void userExistsCheck(Long userId) {
        if (!userRepository.existsById(userId))
            throw new NotFoundException("user is not Found");
    }

    private void locationExistsCheck(Long locationId) {
        if (!locationRepository.existsById(locationId))
            throw new NotFoundException("location is not Found");
    }

}

package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.LocationMapper;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.location.*;
import ru.practicum.model.entity.Event;
import ru.practicum.model.entity.Location;
import ru.practicum.repository.location.LocationRepository;
import ru.practicum.repository.event.EventRepository;
import ru.practicum.service.LocationService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LocationServiceImpl implements LocationService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public LocationFullDto addLocationByAdmin(LocationDto request) {
        log.info("добавление локации админом: {}", request);
        Location location;
        if (request instanceof ExtendedLocationDto extendedLocation) {
            location = locationMapper.extendedDtoToLocation(extendedLocation);
        } else {
            location = locationMapper.dtoToLocation(request);
        }

       // location.setAvailable(true); // По умолчанию локация доступна?
        Location savedLocation = locationRepository.save(location);
        return locationMapper.toLocationFullDto(savedLocation);
    }

    @Override
    @Transactional
    public void deleteLocationByAdmin(Long locationId) {
        log.info("удааление локации админом, Id: {}", locationId);
        if (!locationRepository.existsById(locationId))
            throw new NotFoundException("Location not found with ID: " + locationId);
        locationRepository.deleteById(locationId);
    }

    @Override
    @Transactional
    public LocationFullDto updateLocationByAdmin(Long locationId, UpdateLocationAdminRequest request) {
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
        return locationMapper.toLocationFullDto(updatedLocation);
    }

    @Override
    public LocationFullDto getOneLocationByAdmin(Long locationId) {
        log.info("получение одной локации админом, id локации: {}", locationId);
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location not found with ID: " + locationId));
        return locationMapper.toLocationFullDto(location);
    }

    @Override
    public List<LocationFullDto> getLocationsByAdmin(String name, Double lat, Double lon, Double radius,
                                                     Boolean available, Integer from, Integer size) {
        log.info("получение админом локаций с фильтрами: name={}, lat={}, lon={}, radius={}, available={}, from={}, size={}",
                name, lat, lon, radius, available, from, size);

        Pageable pageable = PageRequest.of(from / size, size);
        Specification<Location> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (lat != null && lon != null && radius != null) {
            // Логика фильтрации по радиусу??
        }
        if (available != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("available"), available));
        }

        Page<Location> locations = locationRepository.findAll(spec, pageable);
        return locations.stream()
                .map(locationMapper::toLocationFullDto)
                .collect(Collectors.toList());
    }



    ///////////////////////////////////////////////
    //////////////////////////////////////////////

    @Override
    public ShortLocationDto getLocationByIdPublic(Long id) {
        log.info("получение локаций по id: {}", id);
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Location not found with ID: " + id));
        return locationMapper.toShortLocationDto(location);
    }

    @Override
    public List<ShortLocationDto> getAllLocationsPublic(String name, Double lat, Double lon, Double radius,
                                                        Integer from, Integer size) { //TODO пользователи получают только доступные локации
        log.info("получение локаций public: name={}, lat={}, lon={}, radius={}, from={}, size={}",
                name, lat, lon, radius, from, size);

        Pageable pageable = PageRequest.of(from / size, size);
        Specification<Location> spec = Specification.where((root, query, cb) -> cb.isTrue(root.get("available"))); // Только доступные локации

        if (name != null) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (lat != null && lon != null && radius != null) {
            //что с радиусом? нативные запросы, функция?
        }

        Page<Location> locations = locationRepository.findAll(spec, pageable);
        return locations.stream()
                .map(locationMapper::toShortLocationDto)
                .collect(Collectors.toList());
    }




    ///////////////////////////////////////////////
    //////////////////////////////////////////////



    @Override
    public List<EventFullDto> getEventsByLocationIdPrivate(Long id, Integer from, Integer size, Boolean onlyAvailable) {
        log.info("получение событий для локации: {} с параметрами: from={}, size={}, onlyAvailable={}", id, from, size, onlyAvailable);

        Pageable pageable = PageRequest.of(from / size, size);
        Specification<Event> spec = Specification.where((root, query, cb) -> cb.equal(root.get("location").get("id"), id));

       /* if (onlyAvailable != null && onlyAvailable) {
            spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("participantLimit"), root.get("confirmedRequests")));
        }*/ // убрать мб?

        Page<Event> events = eventRepository.findAll(spec, pageable);
        return events.stream()
                .map(eventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShortLocationDto> getFavoriteLocationsPrivate(Long userId, Integer from, Integer size) {
        log.info("получение избранных локаций для юзера с ID: {} с фильтрами: from={}, size={}", userId, from, size);

        Pageable pageable = PageRequest.of(from / size, size);

        List<Long> favoriteLocationIds = locationRepository.getFavoriteLocationIdsForUser(userId);
        if (favoriteLocationIds.isEmpty()) {
            return Collections.emptyList();
        }

        Page<Location> locations = locationRepository.findAllByIdIn(favoriteLocationIds, pageable);
        return locations.stream()
                .map(locationMapper::toShortLocationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShortLocationDto addFavoriteLocationPrivate(Long userId, Long locationId) {
        log.info("добавление локации в избранное: {} для пользователя: {}", locationId, userId);

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location not found with ID: " + locationId));

        locationRepository.addLocationToUserFavorites(userId, locationId);
        return locationMapper.toShortLocationDto(location);
    }

    @Override
    @Transactional
    public ShortLocationDto removeFavoriteLocationPrivate(Long userId, Long locationId) {
        log.info("удалене локации с ид: {} из избранного для пользователя ID: {}", locationId, userId);

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location not found with ID: " + locationId));

        locationRepository.removeLocationFromUserFavorites(userId, locationId);
        return locationMapper.toShortLocationDto(location);
    }

}

package ru.practicum.controller.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.event.EventShortDto;
import ru.practicum.model.dto.location.LocationFullDto;
import ru.practicum.model.dto.location.ShortLocationDto;
import ru.practicum.service.LocationService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/public/locations")
@RequiredArgsConstructor
public class PublicLocationController {
    private final LocationService locationService;

    @GetMapping
    public List<ShortLocationDto> findLocationsPublic(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /public/locations/ is accessed with name={}", name);
        return locationService.findLocationsPublic(name, from, size);
    }

    @GetMapping("/{locationId}")
    public LocationFullDto getLocationByIdPublic(@PathVariable Long locationId) {
        log.info("GET /public/locations/{} is accessed", locationId);
        return locationService.getLocationByIdPublic(locationId);
    }

    @GetMapping("/{locationId}/events")
    public List<EventShortDto> getEventsByLocationIdPublic(
            @PathVariable Long locationId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable) {
        log.info("GET /public/locations/{}/events is accessed", locationId);
        return locationService.getEventsByLocationIdPublic(locationId, from, size, onlyAvailable);
    }

    @GetMapping("/coordinates")
    public List<EventShortDto> findEventsByCoordinatesPublic(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam Double radius,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "false") Boolean onlyAvailableEvents) {
        log.info("GET /public/locations/ is accessed with lan={},lon={},radius={}", lat, lon, radius);
        return locationService.getEventsByCoordinatesPublic(lat, lon, radius, from, size, onlyAvailableEvents);
    }
}
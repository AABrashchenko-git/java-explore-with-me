package ru.practicum.controller.privateApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.location.LocationFullDto;
import ru.practicum.model.dto.location.ShortLocationDto;
import ru.practicum.service.LocationService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/private/locations")
@RequiredArgsConstructor
public class PrivateLocationController {
    private final LocationService locationService;

    @GetMapping("/{id}/events")
    public List<EventFullDto> getEventsByLocationIdPrivate(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Boolean onlyAvailable) {
        log.info("GET /private/locations/{}/events is accessed", id);
        return locationService.getEventsByLocationIdPrivate(id, from, size, onlyAvailable);
    }

    @GetMapping("/user/{userId}/favorites")
    public List<ShortLocationDto> getFavoriteLocationsPrivate(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /private/locations/favorites is accessed for user {}", userId);
        return locationService.getFavoriteLocationsPrivate(userId, from, size);
    }

    @PostMapping("/user/{userId}/favorites/favorites/{locationId}")
    public ShortLocationDto addFavoriteLocationPrivate(@RequestParam Long userId, @PathVariable Long locationId) {
        log.info("POST /private/locations/favorites/{} is accessed for user {}", locationId, userId);
        return locationService.addFavoriteLocationPrivate(userId, locationId);
    }

    @DeleteMapping("/user/{userId}/favorites/favorites/{locationId}")
    public ShortLocationDto removeFavoriteLocationPrivate(@PathVariable Long userId, @RequestParam Long locationId) {
        log.info("DELETE /private/locations/favorites/{} is accessed for user {}", locationId, userId);
        return locationService.removeFavoriteLocationPrivate(userId, locationId);
    }


}
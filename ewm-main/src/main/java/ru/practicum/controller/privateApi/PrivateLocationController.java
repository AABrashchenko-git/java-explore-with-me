package ru.practicum.controller.privateApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.location.LocationFullResponseDto;
import ru.practicum.model.dto.location.LocationShortResponseDto;
import ru.practicum.service.LocationService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/private/locations")
@RequiredArgsConstructor
public class PrivateLocationController {
    private final LocationService locationService;

    @GetMapping("/user/{userId}/favorites")
    public List<LocationShortResponseDto> getFavoriteLocationsPrivate(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /private/locations/favorites is accessed for user {}", userId);
        return locationService.getFavoriteLocationsPrivate(userId, from, size);
    }

    @PostMapping("/user/{userId}/favorites/{locationId}")
    public LocationShortResponseDto addFavoriteLocationPrivate(@PathVariable Long userId, @PathVariable Long locationId) {
        log.info("POST /private/locations/favorites/{} is accessed for user {}", locationId, userId);
        return locationService.addFavoriteLocationPrivate(userId, locationId);
    }

    @DeleteMapping("/user/{userId}/favorites/{locationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavoriteLocationPrivate(@PathVariable Long userId, @PathVariable Long locationId) {
        log.info("DELETE /private/locations/favorites/{} is accessed for user {}", locationId, userId);
        locationService.removeFavoriteLocationPrivate(userId, locationId);
    }
}
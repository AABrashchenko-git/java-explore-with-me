package ru.practicum.controller.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.location.ShortLocationDto;
import ru.practicum.service.LocationService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/public/locations")
@RequiredArgsConstructor
public class PublicLocationController {
    private final LocationService locationService;

    @GetMapping("/{id}")
    public ShortLocationDto getLocationByIdPublic(@PathVariable Long id) {
        log.info("GET /public/locations/{} is accessed", id);
        return locationService.getLocationByIdPublic(id);
    }

    @GetMapping
    public List<ShortLocationDto> getAllLocationsPublic(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) Double radius,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /public/locations is accessed");
        return locationService.getAllLocationsPublic(name, lat, lon, radius, from, size);
    }
}
package ru.practicum.controller.adminApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.dto.event.EventFullDto;
import ru.practicum.model.dto.event.UpdateEventAdminRequest;
import ru.practicum.model.dto.location.LocationDto;
import ru.practicum.model.dto.location.LocationFullResponseDto;
import ru.practicum.model.dto.location.LocationShortResponseDto;
import ru.practicum.model.dto.location.UpdateLocationAdminRequest;
import ru.practicum.service.EventService;
import ru.practicum.service.LocationService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin/locations")
@RequiredArgsConstructor
public class AdminLocationController {
    private final LocationService locationService;

    @PostMapping
    public LocationFullResponseDto addLocationByAdmin(@Valid @RequestBody LocationDto request) {
        log.info("POST /admin/locations is accessed: {}", request);
        return locationService.addLocationByAdmin(request);
    }

    @DeleteMapping("/{locationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocationByAdmin(@PathVariable Long locationId) {
        log.info("DELETE /admin/events/{} is accessed", locationId);
        locationService.deleteLocationByAdmin(locationId);
    }


    @PatchMapping("/{locationId}")
    public LocationFullResponseDto updateLocationByAdmin(@PathVariable Long locationId,
                                                 @Valid @RequestBody UpdateLocationAdminRequest request) {
        log.info("PATCH /admin/locations/{} is accessed: {}", locationId, request);
        return locationService.updateLocationByAdmin(locationId, request);
    }

    @GetMapping
    public List<LocationShortResponseDto> findLocationsByAdmin(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "false") Boolean available,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /admin/locations is accessed");
        return locationService.findLocationsByAdmin(name, available, from, size);
    }
}
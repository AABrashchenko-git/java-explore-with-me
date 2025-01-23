package ru.practicum.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.model.dto.location.ExtendedLocationDto;
import ru.practicum.model.dto.location.LocationDto;

import java.io.IOException;

@Slf4j
public class LocationDtoDeserializer extends JsonDeserializer<LocationDto> {
    @Override
    public LocationDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.has("name") && node.has("available")) {
            log.debug("Creating ExtendedLocationDto from JSON");
            ExtendedLocationDto extendedLocation = new ExtendedLocationDto();
            extendedLocation.setLat(node.get("lat").asDouble());
            extendedLocation.setLon(node.get("lon").asDouble());
            extendedLocation.setName(node.get("name").asText());
            extendedLocation.setAvailable(node.get("available").asBoolean());
            return extendedLocation;
        } else {
            log.debug("Creating LocationDto from JSON");
            LocationDto location = new LocationDto();
            location.setLat(node.get("lat").asDouble());
            location.setLon(node.get("lon").asDouble());
            return location;
        }
    }
}

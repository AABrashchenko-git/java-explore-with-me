package ru.practicum.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.practicum.model.dto.location.ExtendedLocationDto;
import ru.practicum.model.dto.location.LocationDto;

import java.io.IOException;

public class LocationDtoDeserializer extends JsonDeserializer<LocationDto> {
    @Override
    public LocationDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.has("name") && node.has("radius")) {
            ExtendedLocationDto extendedLocation = new ExtendedLocationDto();
            extendedLocation.setLat(node.get("lat").asDouble());
            extendedLocation.setLon(node.get("lon").asDouble());
            extendedLocation.setName(node.get("name").asText());
            extendedLocation.setRadius(node.get("radius").asDouble());
            return extendedLocation;
        } else {
            LocationDto location = new LocationDto();
            location.setLat(node.get("lat").asDouble());
            location.setLon(node.get("lon").asDouble());
            return location;
        }
    }
}

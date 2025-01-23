package ru.practicum.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.model.dto.location.LocationDto;

import java.io.IOException;

@Slf4j
public class LocationDtoDeserializer extends JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // чтобы при создании событий можно было указывать и локацию, уже существующую в системе (её id), и просто координаты
        // поэтому в NewEventDto поле Object location (в итоге будет или идентификатор существующей или объект LocationDto)
        JsonNode node = p.getCodec().readTree(p);
        if (node.isObject()) {
            log.debug("Creating LocationDto from JSON");
            return p.getCodec().treeToValue(node, LocationDto.class);
        } else if (node.isNumber()) {
            log.debug("Creating ID from JSON");
            return node.asLong();
        } else {
            throw new IllegalArgumentException("передаваемая локация должна включать требуемые поля LocationDto или идентификатор (id)");
        }
    }
}
package ru.practicum.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.enums.RequestStatus;

import java.time.LocalDateTime;

import static ru.practicum.config.DateTimeFormats.DATE_TIME_PATTERN;

@Data
@AllArgsConstructor
public class ParticipationRequestDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @NotNull
    private LocalDateTime created;
    @NotNull
    private Long event;
    @NotNull
    private Long requester;
    @NotNull
    private RequestStatus status;
}

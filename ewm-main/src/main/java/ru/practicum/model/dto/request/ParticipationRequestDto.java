package ru.practicum.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.enums.RequestStatus;

import java.time.LocalDateTime;

import static ru.practicum.config.DateTimeFormats.DATE_TIME_PATTERN;

@Data
//1. возвращается private пользователю, оставившему заявку на участие в событии
// (при получении текущих заявок, при добавлении заявки, при отмене заявки)
// 2. возвращается пользователю (текущему), показывает информацию о запросах на участии в данном событии таеущего пользователя
@AllArgsConstructor
public class ParticipationRequestDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime created;
    // или Long eventId;  @JsonProperty("event")? аналогично для Long requester?
    private Long event;
    private Long requester;
    private RequestStatus status;
}

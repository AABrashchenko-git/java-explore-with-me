package ru.practicum.model.dto.request;

import lombok.Data;
import ru.practicum.enums.RequestStatus;

import java.time.LocalDateTime;

@Data
//1. возвращается private пользователю, оставившему заявку на участие в событии
// (при получении текущих заявок, при добавлении заявки, при отмене заявки)
// 2. возвращается пользователю (текущему), показывает информацию о запросах на участии в данном событии таеущего пользователя
public class ParticipationRequestDto {
    private Long id;
    private LocalDateTime created;
    // или Long eventId;  @JsonProperty("event")? аналогично для Long requester?
    private Long event;
    private Long requester;
    private RequestStatus status;
}

package ru.practicum.model.dto.request;

import lombok.Data;
import ru.practicum.enums.RequestStatus;

import java.util.List;

@Data
// запрос: текущий пользователь изменяет статус заявок на участие в событии (подтверждена, отменена)
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private RequestStatus status;
}

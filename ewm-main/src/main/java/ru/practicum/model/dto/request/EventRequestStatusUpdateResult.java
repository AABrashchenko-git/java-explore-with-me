package ru.practicum.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateResult {
    @NotNull
    private List<ParticipationRequestDto> confirmedRequests;
    @NotNull
    private List<ParticipationRequestDto> rejectedRequests;
}
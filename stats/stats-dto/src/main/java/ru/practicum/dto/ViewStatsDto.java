package ru.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ViewStatsDto {
    @NotBlank(message = "app should not be empty")
    private String app;
    @NotBlank(message = "uri should not be empty")
    private String uri;
    @NotNull(message = "hits must not be null")
    private Integer hits;
}

package ru.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class EndpointHitDto {
    private Integer id;
    @NotBlank(message = "app should not be empty")
    private String app;
    @NotBlank(message = "uri should not be empty")
    private String uri;
    @NotBlank(message = "ip should not be empty")
    private String ip;
    @NotBlank(message = "timestamp should not be empty")
    private String timestamp;
}

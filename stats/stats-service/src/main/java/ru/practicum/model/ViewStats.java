package ru.practicum.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ViewStats {
    @NotBlank(message = "App must not be blank")
    private String app;
    @NotBlank(message = "URI must not be blank")
    private String uri;
    @NotNull(message = "hits must not be null")
    private Long hits;
}
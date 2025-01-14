package ru.practicum.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserShortDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
}

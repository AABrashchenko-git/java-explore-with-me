package ru.practicum.model.entity;

import lombok.Data;

@Data
public class Location {
    private Long id;
    private Double lat;
    private Double lon;
}
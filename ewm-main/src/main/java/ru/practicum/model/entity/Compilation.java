package ru.practicum.model.entity;

import java.util.Set;

public class Compilation {
    private Long id;
    private String title;
    private Boolean pinned;
    private Set<Event> events;
}

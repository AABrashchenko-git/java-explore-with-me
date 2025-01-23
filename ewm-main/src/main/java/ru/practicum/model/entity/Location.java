package ru.practicum.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.utils.NotBlankIfPresent;

@Entity
@Table(name = "location")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
    @NotBlankIfPresent
    private String name;
    private Boolean available;
}
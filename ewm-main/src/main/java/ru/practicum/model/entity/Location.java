package ru.practicum.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Double lat;
    private Double lon;
}
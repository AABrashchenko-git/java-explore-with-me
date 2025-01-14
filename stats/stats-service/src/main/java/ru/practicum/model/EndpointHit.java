package ru.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stats")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class EndpointHit {
    @Id
    @Column(name = "stats_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "App must not be blank")
    private String app;
    @NotBlank(message = "URI must not be blank")
    private String uri;
    @NotBlank(message = "IP must not be blank")
    private String ip;
    @PastOrPresent(message = "Timestamp must be in the past or present")
    private LocalDateTime timestamp;
}
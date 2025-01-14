package ru.practicum.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.enums.RequestStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"event", "requester"})
@EqualsAndHashCode(of = {"id"})
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;
    @NotNull
    private LocalDateTime created;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @NotNull
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    @NotNull
    private User requester;
    @Enumerated(EnumType.STRING)
    @NotNull
    private RequestStatus status;
}

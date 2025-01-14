package ru.practicum.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.dto.request.ParticipationRequestDto;
import ru.practicum.model.entity.ParticipationRequest;

import java.util.List;
import java.util.Optional;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    @Query("SELECT p FROM ParticipationRequest p " +
            "JOIN FETCH p.event e " +
            "JOIN FETCH p.requester r " +
            "WHERE e.initiator.id = :userId")
    List<ParticipationRequest> findAllByEventInitiatorId(@Param("userId") Long userId);

    @Query("SELECT new ru.practicum.model.dto.request.ParticipationRequestDto(pr.id, pr.created, pr.event.id, pr.requester.id, pr.status) " +
            "FROM ParticipationRequest pr " +
            "WHERE pr.requester.id = :requesterId")
    List<ParticipationRequestDto> findDtoByUserId(@Param("requesterId") Long requesterId);

    @EntityGraph(attributePaths = {"requester"})
    List<ParticipationRequest> findAllByEventId(Long eventId);

    Optional<ParticipationRequest> findByIdAndRequesterId(Long id, Long requesterId);
}

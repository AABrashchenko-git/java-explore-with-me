package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.dto.request.ParticipationRequestDto;
import ru.practicum.model.entity.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    @Query("SELECT p FROM ParticipationRequest p " +
            "JOIN FETCH p.event e " +
            "JOIN FETCH p.requester r " +
            "WHERE e.initiator.id = :userId")
    List<ParticipationRequest> findAllByEventInitiatorId(@Param("userId") Long userId);
}

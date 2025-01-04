package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {

    @Query("SELECT new ru.practicum.model.ViewStats(e.app, e.uri, COUNT(DISTINCT e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(DISTINCT e.ip) DESC")
    List<ViewStats> findAllByParamsUnique(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.model.ViewStats(e.app, e.uri, COUNT(DISTINCT e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND e.uri IN :uris " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(DISTINCT e.ip) DESC")
    List<ViewStats> findAllByParamsAndUrisUnique(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                                 @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.model.ViewStats(e.app, e.uri, COUNT(e)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e) DESC")
    List<ViewStats> findAllByParams(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.model.ViewStats(e.app, e.uri, COUNT(e)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND e.uri IN :uris " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e) DESC")
    List<ViewStats> findAllByParamsAndUris(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
                                           @Param("uris") List<String> uris);
}

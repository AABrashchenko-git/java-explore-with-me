package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.mapper.ViewStatsMapper;
import ru.practicum.model.ViewStats;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private final EndpointHitMapper hitMapper;
    private final ViewStatsMapper viewMapper;

    @Override
    public EndpointHitDto addStats(EndpointHitDto endpointHitDto) {
        return hitMapper.endpointHitToDto(statsRepository.save(hitMapper.hitDtoToEntity(endpointHitDto)));
    }

    @Override
    public List<ViewStatsDto> getStats(String start, String end, List<String> uris, Boolean unique) {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateStart = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime dateEnd = LocalDateTime.parse(end, FORMATTER);

        List<ViewStats> result;

        if (unique) {
            result = uris != null ? statsRepository.findAllByParamsAndUrisUnique(dateStart, dateEnd, uris)
                    : statsRepository.findAllByParamsUnique(dateStart, dateEnd);
        } else {
            result = uris != null ? statsRepository.findAllByParamsAndUris(dateStart, dateEnd, uris)
                    : statsRepository.findAllByParams(dateStart, dateEnd);
        }
        return result.stream()
                .map(viewMapper::viewStatsToDto).collect(Collectors.toList());
    }
}

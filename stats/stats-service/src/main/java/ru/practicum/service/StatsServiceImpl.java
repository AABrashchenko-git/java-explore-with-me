package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import static ru.practicum.config.DateTimeFormats.DATE_TIME_PATTERN;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private final EndpointHitMapper hitMapper;
    private final ViewStatsMapper viewMapper;

    @Transactional
    @Override
    public EndpointHitDto addStats(EndpointHitDto endpointHitDto) {
        return hitMapper.endpointHitToDto(statsRepository.save(hitMapper.hitDtoToEntity(endpointHitDto)));
    }

    @Override
    public List<ViewStatsDto> getStats(String start, String end, List<String> uris, Boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDateTime dateStart = LocalDateTime.parse(start, formatter);
        LocalDateTime dateEnd = LocalDateTime.parse(end, formatter);

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

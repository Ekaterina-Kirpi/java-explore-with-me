package ru.practicum.ewm.stats.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.server.EndpointHitDto;
import ru.practicum.ewm.stats.server.ViewStatsDto;
import ru.practicum.ewm.stats.client.mapper.EndpointHitMapper;
import ru.practicum.ewm.stats.client.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository statsRepository;

    public void saveHit(EndpointHitDto hit) {
        statsRepository.save(EndpointHitMapper.toHitFromHitDto(hit));
    }

    @Transactional(readOnly = true)
    public List<ViewStatsDto> getVisitStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean uniq) {
        if (start.isAfter(end)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверно указано время");
        }
        if (uniq) {
            if (uris != null) {
                return statsRepository.findHitsWithUniqueIpWithUris(uris, start, end);
            }
            return statsRepository.findHitsWithUniqueIpWithoutUris(start, end);
        } else {
            if (uris != null) {
                return statsRepository.findAllHitsWithUris(uris, start, end);
            }
            return statsRepository.findAllHitsWithoutUris(start, end);
        }
    }
}

package ru.practicum.ewm.stats.server;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStats;
import ru.practicum.ewm.stats.server.model.EndpointHitMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository statsRepository;

    public void saveHit(EndpointHitDto hit) {
        statsRepository.save(EndpointHitMapper.toHit(hit));
    }

    @Transactional(readOnly = true)
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start.isAfter(end)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong timestamp.");
        }
        return unique ?
                uris != null ? statsRepository.findHitsWithUniqueIpWithUris(uris, start, end) : statsRepository.findHitsWithUniqueIpWithoutUris(start, end) :
                uris != null ? statsRepository.findAllHitsWithUris(uris, start, end) : statsRepository.findAllHitsWithoutUris(start, end);
    }
}



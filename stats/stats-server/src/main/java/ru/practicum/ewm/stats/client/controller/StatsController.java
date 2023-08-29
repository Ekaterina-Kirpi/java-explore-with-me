package ru.practicum.ewm.stats.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.server.EndpointHitDto;
import ru.practicum.ewm.stats.client.service.StatsService;
import ru.practicum.ewm.stats.server.ViewStatsDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public void saveHit(@RequestBody @Valid EndpointHitDto hit) {
        statsService.saveHit(hit);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getVisitStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                       @RequestParam(required = false) List<String> uris,
                                       @RequestParam(defaultValue = "false") Boolean uniq) {
        return statsService.getVisitStats(start, end, uris, uniq);
    }


}

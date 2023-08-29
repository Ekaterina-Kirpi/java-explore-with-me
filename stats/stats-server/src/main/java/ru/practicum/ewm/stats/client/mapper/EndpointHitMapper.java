package ru.practicum.ewm.stats.client.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.stats.server.EndpointHitDto;
import ru.practicum.ewm.stats.client.model.EndpointHit;

@UtilityClass
public class EndpointHitMapper {
    public EndpointHit toHitFromHitDto(EndpointHitDto hit) {
        return new EndpointHit(
                hit.getId(),
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                hit.getTimestamp()
        );
    }
}


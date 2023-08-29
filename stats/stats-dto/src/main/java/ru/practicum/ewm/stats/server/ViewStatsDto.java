package ru.practicum.ewm.stats.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewStatsDto {
    private String app;
    private String uri;
    private String hits;
}

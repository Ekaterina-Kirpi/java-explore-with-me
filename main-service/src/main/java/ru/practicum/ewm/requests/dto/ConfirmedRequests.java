package ru.practicum.ewm.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ConfirmedRequests {
    private long count;
    private Long event;
}

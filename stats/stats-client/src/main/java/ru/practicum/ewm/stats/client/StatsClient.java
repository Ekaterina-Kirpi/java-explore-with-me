package ru.practicum.ewm.stats.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.stats.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsClient {
    @Value("${client.url}")
    private String serverUrl;
    private final RestTemplate rest;

    public StatsClient() {
        this.rest = new RestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        rest.setRequestFactory(requestFactory);
    }
   /* public StatsClient(RestTemplate rest) {
        super(rest);
    }

    */

    public ResponseEntity<Object> saveHit(EndpointHitDto hit) {
        try {
            ResponseEntity<Object> response = rest.postForEntity(serverUrl + "/hit", hit, Object.class);
            ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
            if (response.hasBody()) {
                return responseBuilder.body(response.getBody());
            }
            return responseBuilder.build();
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        HashMap<String, Object> params = new HashMap<>(Map.of("start", start,
                "end", end,
                "uris", uris,
                "unique", unique));
        try {
            ResponseEntity<Object> response = rest.getForEntity(serverUrl + "/stats", Object.class, params);

            ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
            if (response.hasBody()) {
                return responseBuilder.body(response.getBody());
            }
            return responseBuilder.build();
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
    }
}
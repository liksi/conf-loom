package fr.liksi.confloom.springmvcserver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class GreatApiClient {

    private final RestTemplate restTemplate;

    private final String baseUrl;

    public GreatApiClient(RestTemplateBuilder restTemplateBuilder, @Value("${ext.great-api.baseUrl}") String baseUrl) {
        this.restTemplate = restTemplateBuilder.build();
        this.baseUrl = baseUrl;
    }

    @GetExchange("/get-doors-stats")
    public List<ApiDoorStat> getDoorsStats(@RequestParam int delay) {
        ApiDoorStat[] stats = restTemplate.getForObject(baseUrl + "/get-doors-stats?delay={delay}", ApiDoorStat[].class, delay);

        return Arrays.stream(Objects.requireNonNull(stats)).toList();
    }

    public record ApiDoorStat(String doorName, int nbOpening) {}
}


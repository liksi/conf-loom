package fr.liksi.confloom.springmvcserver.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@HttpExchange
public interface GreatApiClient {

    @GetExchange("/get-doors-stats")
    Mono<List<ApiDoorStat>> getDoorsStats(@RequestParam int delay);

    record ApiDoorStat(String doorName, int nbOpening) {}
}


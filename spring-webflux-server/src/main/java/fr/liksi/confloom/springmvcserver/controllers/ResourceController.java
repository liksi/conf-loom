package fr.liksi.confloom.springmvcserver.controllers;

import fr.liksi.confloom.springmvcserver.service.GreatApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@RestController
public class ResourceController {

    final GreatApiClient greatApiClient;

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    public ResourceController(GreatApiClient greatApiClient) {
        this.greatApiClient = greatApiClient;
    }

    @GetMapping("/api")
    public Mono<String> getNextDoorToOpen() {
        logger.info("Receiving request, calling great API");
        final var stats = greatApiClient.getDoorsStats(3);
        logger.info("Great API finished");

        return stats
                .doOnNext(res -> logger.info("Great API returned [{}]", res))

                .map(res -> res.stream()
                        .min(Comparator.comparing(GreatApiClient.ApiDoorStat::nbOpening))
                        .orElse(new GreatApiClient.ApiDoorStat("RUN !!!", 0))
                        .doorName())

                .doOnNext(door -> logger.info("Selected door [{}]", door));
    }
}

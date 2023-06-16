package fr.liksi.confloom.springmvcserver.controllers;

import fr.liksi.confloom.springmvcserver.service.GreatApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;

@RestController
public class ResourceController {

    private final GreatApiClient greatApiClient;

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    public ResourceController(GreatApiClient greatApiClient) {
        this.greatApiClient = greatApiClient;
    }

    @GetMapping("/api")
    public String getNextDoorToOpen() {
        logger.info("Receiving request, calling great API");
        final var stats = greatApiClient.getDoorsStats(5);
        logger.info("Great API returned [{}]", stats);

        String door = stats.stream()
                .min(Comparator.comparing(GreatApiClient.ApiDoorStat::nbOpening))
                .orElse(new GreatApiClient.ApiDoorStat("RUN !!!", 0))
                .doorName();

        logger.info("Selected door [{}]", door);

        return door;
    }

}

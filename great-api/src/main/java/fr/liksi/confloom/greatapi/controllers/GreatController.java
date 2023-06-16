package fr.liksi.confloom.greatapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
public class GreatController {

    private static final Logger logger = LoggerFactory.getLogger(GreatController.class);

    @GetMapping("/get-doors-stats")
    public List<ApiDoorStat> getDoorsStats(@RequestParam(name = "delay") int delay) throws InterruptedException {
        logger.info("I will be waiting for {} seconds", delay);
        doGeolocalisation(delay);
        final var random = new Random();
        return List.of(
                new ApiDoorStat("A", random.nextInt(100)),
                new ApiDoorStat("B", random.nextInt(100)),
                new ApiDoorStat("C", random.nextInt(100))
                );
    }

    private void doGeolocalisation(int delay) throws InterruptedException {
        Thread.sleep(delay*1000);
    }

    public record ApiDoorStat(String doorName, int nbOpening) {}
}





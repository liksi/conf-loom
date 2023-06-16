package fr.liksi.confloom.springmvcserver.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface GreatApiClient {

    @GetExchange("/get-doors-stats")
    List<ApiDoorStat> getDoorsStats(@RequestParam int delay);

    record ApiDoorStat(String doorName, int nbOpening) {}
}


package com.codechallenge.itineraryservice.controller;

import com.codechallenge.itineraryservice.model.Itinerary;
import com.codechallenge.itineraryservice.service.ItineraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${general.endpoint}")
@Slf4j
public class ItineraryController {


    @Autowired
    ItineraryService itineraryService;

    @RequestMapping(value = "/{originCity}/{destinationCity}", method = RequestMethod.GET)
    Itinerary getItinerary(@PathVariable String originCity, @PathVariable String destinationCity) {

        log.debug("Looking for a new itinerary between %s and %s", originCity, destinationCity);

        return itineraryService.getItinerary(originCity, destinationCity);
    }


}

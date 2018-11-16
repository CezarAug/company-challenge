package com.codechallenge.itineraryservice.controller;

import com.codechallenge.itineraryservice.error.ApiError;
import com.codechallenge.itineraryservice.model.Itinerary;
import com.codechallenge.itineraryservice.service.ItineraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${general.endpoint}")
@Slf4j
@Api(value="itineraryservice", description="Itinerary service endpoint. Available for all users")
public class ItineraryController {


    @Autowired
    ItineraryService itineraryService;


    @ApiOperation(
            consumes = "application/json",
            produces = "application/json",
            value = "Filters the shortest (less connections) and quickest itineraries between two cities"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a list of routes."),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Any Internal server errors", response = ApiError.class)}
    )
    @RequestMapping(value = "/{originCity}/{destinationCity}", method = RequestMethod.GET)
    Itinerary getItinerary(@PathVariable String originCity, @PathVariable String destinationCity) {

        log.debug("Looking for a new itinerary between %s and %s", originCity, destinationCity);

        return itineraryService.getItinerary(originCity, destinationCity);
    }


}

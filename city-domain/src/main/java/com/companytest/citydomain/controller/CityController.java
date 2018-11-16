package com.companytest.citydomain.controller;

import com.companytest.citydomain.model.City;
import com.companytest.citydomain.service.CityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("${general.endpoint:/city}")
public class CityController {

    @Autowired
    CityService cityService;

    @GetMapping()
    List<City> findAllCities() {
        return cityService.findAll();
    }


    @ApiOperation(
            consumes = "application/json",
            produces = "application/json",
            value = "All route registers for the provided origin city")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a list of routes starting from the provided city"),
            @ApiResponse(code = 403, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Any Internal server errors")}
    )
    @RequestMapping(value = "/{cityName}", method = RequestMethod.GET, produces = "application/json")
    List<City> findRoutesByCityName(@PathVariable String cityName) {
        return cityService.findByCityName(cityName);
    }

    @ApiOperation(
            consumes = "application/json",
            produces = "application/json",
            value = "Creates a new register for a route")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful confirmation message"),
            @ApiResponse(code = 403, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Any Internal server errors")}
    )
    @PostMapping()
    String postCityList(@Valid @RequestBody List<City> city) {
        cityService.saveCity(city);
        return "OK";
    }

    @ApiOperation(
            consumes = "application/json",
            produces = "application/json",
            value = "Deletes all routes from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation successful confirmation message"),
            @ApiResponse(code = 403, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Any Internal server errors")}
    )
    @DeleteMapping
    String deleteAllCities(){
        cityService.deleteAll();
        return "OK";
    }

}

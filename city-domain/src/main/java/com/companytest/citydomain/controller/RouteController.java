package com.companytest.citydomain.controller;

import com.companytest.citydomain.error.ApiError;
import com.companytest.citydomain.model.Route;
import com.companytest.citydomain.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("${general.endpoint}")
@Api(value="routemanager", description="Operations for routes in Travel Itinerary services. Admins only")
public class RouteController {

    @Autowired
    RouteService routeService;

    @ApiOperation(
            consumes = "application/json",
            produces = "application/json",
            value = "Returns all routes registered."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a list of routes."),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Any Internal server errors", response = ApiError.class)}
    )
    @GetMapping()
    List<Route> findAllCities() {
        return routeService.findAll();
    }

    @ApiOperation(
            consumes = "application/json",
            produces = "application/json",
            value = "Returns all routes registered for the provided origin city"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a list of routes starting from the provided city"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Any Internal server errors", response = ApiError.class)}
    )
    @RequestMapping(value = "/{cityName}", method = RequestMethod.GET, produces = "application/json")
    List<Route> findRoutesByCityName(@PathVariable String cityName) {
        return routeService.findByCityName(cityName);
    }

    @ApiOperation(
            consumes = "application/json",
            produces = "application/json",
            value = "Creates new routes based on the provided list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a list of routes starting from the provided city"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Any Internal server errors", response = ApiError.class)}
    )
    @PostMapping()
    String postCityList(@Valid @RequestBody List<Route> route) {
        routeService.saveCity(route);
        return "OK";
    }

    @ApiOperation(
            consumes = "application/json",
            produces = "application/json",
            value = "Deletes all routes from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns a list of routes starting from the provided city"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Any Internal server errors", response = ApiError.class)}
    )
    @DeleteMapping
    String deleteAllCities(){
        routeService.deleteAll();
        return "OK";
    }

}

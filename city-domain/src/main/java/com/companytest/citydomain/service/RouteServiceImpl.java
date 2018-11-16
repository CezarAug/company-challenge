package com.companytest.citydomain.service;

import com.companytest.citydomain.model.Route;
import com.companytest.citydomain.repository.RouteRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    RouteRepository routeRepository;

    @Override
    @HystrixCommand(fallbackMethod="getFallbackRoutes")
    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    @Override
    @HystrixCommand(fallbackMethod="getFilteredFallbackRoutes")
    public List<Route> findByCityName(String cityName) {
        return routeRepository.findByCity(cityName);
    }

    @Override
    public void saveCity(List<Route> route) {
        routeRepository.saveAll(route);
    }

    @Override
    public void deleteAll() {
        routeRepository.deleteAll();
    }


    //If the service fails, Hystrix will return a pre-defined list of routes.
    public List<Route> getFallbackRoutes() {
        return generateFallbackRoutesList();
    }

    public List<Route> getFilteredFallbackRoutes(String cityName) {
        return generateFallbackRoutesList()
                .stream()
                .filter(route -> route.getCity().equals(cityName))
                .collect(Collectors.toList());
    }


    private List<Route> generateFallbackRoutesList() {
        List<Route> routes = new ArrayList<>();

        routes.add(new Route()
                .setCity("GRU")
                .setDestination("REC")
                .setDepartureTime("00:00")
                .setArrivalTime("07:00")
        );

        routes.add(new Route()
                .setCity("SAO")
                .setDestination("REC")
                .setDepartureTime("00:00")
                .setArrivalTime("06:00")
        );

        routes.add(new Route()
                .setCity("REC")
                .setDestination("GRU")
                .setDepartureTime("04:00")
                .setArrivalTime("06:00")
        );

        routes.add(new Route()
                .setCity("REC")
                .setDestination("SAO")
                .setDepartureTime("04:00")
                .setArrivalTime("06:00")
        );

        return routes;
    }

}

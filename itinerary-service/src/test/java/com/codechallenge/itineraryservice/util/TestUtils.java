package com.codechallenge.itineraryservice.util;

import com.codechallenge.itineraryservice.model.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {

    public static List<Route> generateMockRoutesList() {
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

        routes.add(new Route()
                .setCity("GRU")
                .setDestination("CGH")
                .setDepartureTime("00:00")
                .setArrivalTime("02:00")
        );

        routes.add(new Route()
                .setCity("CGH")
                .setDestination("REC")
                .setDepartureTime("00:00")
                .setArrivalTime("01:00")
        );


        return routes;
    }

    public static List<Route> generateFilteredMockRoutesList(String cityName) {
        return generateMockRoutesList()
                .stream()
                .filter(route -> route.getCity().equals(cityName))
                .collect(Collectors.toList());
    }
}

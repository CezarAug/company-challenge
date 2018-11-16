package com.companytest.citydomain.util;

import com.companytest.citydomain.model.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {

    public static List<Route> generateMockRoutesList() {
        java.util.List<Route> routes = new ArrayList<>();

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

    public static List<Route> generateFilteredMockRoutesList(String cityName) {
        return generateMockRoutesList()
                .stream()
                .filter(route -> route.getCity().equals(cityName))
                .collect(Collectors.toList());
    }
}

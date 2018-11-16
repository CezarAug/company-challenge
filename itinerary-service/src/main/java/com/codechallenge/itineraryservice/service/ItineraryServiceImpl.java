package com.codechallenge.itineraryservice.service;

import com.codechallenge.itineraryservice.dao.RouteDao;
import com.codechallenge.itineraryservice.model.Route;
import com.codechallenge.itineraryservice.model.Itinerary;
import com.codechallenge.itineraryservice.util.TimeUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
@RequestScope
public class ItineraryServiceImpl implements ItineraryService {

    @Autowired
    RouteDao routeDao;

    private List<List<Route>> validRoutes = new ArrayList<>();

    private Integer minTime = Integer.MAX_VALUE;

    @HystrixCommand(fallbackMethod="getFallbackItinerary")
    public Itinerary getItinerary(String originCity, String destinationCity) {
        minTime = Integer.MAX_VALUE;

        this.findAllRoutes(originCity, destinationCity);
        log.info(validRoutes.size() + "Routes located! Filtering by connections and time");

        validRoutes.stream()
                .forEach(this::setMinimumTime);

        return new Itinerary()
                .setDestination(destinationCity)
                .setOrigin(originCity)
                .setFastItinerary(getFastestItinerary())
                .setShortestItinerary(getShortestItinerary());
    }

    /**
     * Checks within all routes list found, which one is the fastest.
     * @return A list containing all fastest routes list
     */
    private List<List<Route>> getFastestItinerary() {
        return validRoutes.stream()
                .filter(validRoute -> this.getTotalTime(validRoute) == minTime)
                .collect(Collectors.toList());

    }

    /**
     * Checks within all routes list found, which one is the shortest (with less connections).
     * @return A list containing all fastest routes list
     */
    private List<List<Route>> getShortestItinerary() {
        return validRoutes.stream()
                .filter(validRoute -> validRoute.size() == validRoutes.stream().mapToInt(shortestRoute -> shortestRoute.size()).min().getAsInt())
                .collect(Collectors.toList());

    }

    /**
     * Locates all possible routes between origin and destination cities based on a first list of possible routes returned by City service
     * If there is any, navigate function will be responsible for validating and proceed with the route build
     * Otherwise, the "validRoutes" will be empty.
     *
     * @param originCity
     * @param destinationCity
     */
    private void findAllRoutes(String originCity, String destinationCity) {
        log.debug("Locating all routes between " + originCity + " and " + destinationCity);

        //getting first routes
        //Origin first connections, if there is a direct path, there is no need to check it further
        Optional<List<Route>> firstDestinations = Optional.ofNullable(routeDao.getCityByName(originCity));


        if (firstDestinations.isPresent() && !firstDestinations.get().isEmpty()) {
            if (validateIfDestinationWasFound(firstDestinations.get(), destinationCity)) {
                firstDestinations.get()
                        .stream()
                        .filter(route -> route.getDestination().equals(destinationCity))
                        .forEach(route -> validRoutes.add(Arrays.asList(route)));

                log.debug("final route found!");

                //Removing any direct route to the destination and sending the others to be iterated
                firstDestinations.get()
                        .stream()
                        .filter(route -> !route.getDestination().equals(destinationCity))
                        .forEach(route -> navigateThroughRoutes(Arrays.asList(route), route, destinationCity));


            } else {
                log.debug("There are no direct routes between " + originCity + " and " + destinationCity);
                firstDestinations.get()
                        .stream()
                        .filter(route -> !route.getDestination().equals(destinationCity))
                        .forEach(route -> navigateThroughRoutes(Arrays.asList(route), route, destinationCity));
            }

        } else {
            log.debug("There are no routes available between " + originCity + " and " + destinationCity);
        }
    }

    /**
     * Iterates trough a list of routes.
     * First it will check if the current route has the same destination as requested
     * Otherwise it will check if the next city is new, if true the function will be called again with a new list.
     *
     * @param routes
     * @param currentRoute
     * @param finalDestination
     */
    private void navigateThroughRoutes(List<Route> routes, Route currentRoute, String finalDestination) {
        Optional<List<Route>> foundRoutes = Optional.ofNullable(routeDao.getCityByName(currentRoute.getDestination()));

        if (validateIfDestinationWasFound(foundRoutes.get(), finalDestination)) {
            //Final destination reached, adding the current path to the validRoutes list
            foundRoutes.get()
                    .stream()
                    .filter(route -> route.getDestination().equals(finalDestination))
                    .forEach(route -> {
                        List<Route> finalRoute = new ArrayList<>();
                        finalRoute.addAll(routes);
                        finalRoute.add(route);
                        validRoutes.add(finalRoute);
                    });

            //Removing found routes from the array to proceed with the rest
            foundRoutes.get().removeIf(route -> route.getDestination().equals(finalDestination));
            log.debug("Route found!");
        }

        //If the destination city is new, let's iterate again. Otherwise, let it go.
        foundRoutes.get()
                .stream()
                .filter(route -> isNewDestination(routes, route.getDestination()))
                .forEach(route -> {
                    List<Route> newRoutes = new ArrayList<>();
                    newRoutes.addAll(routes);
                    newRoutes.add(route);
                    navigateThroughRoutes(newRoutes, route, finalDestination);
                });

    }


    //Minor helper functions just to keep the code cleaner
    /**
     * Checks if the informed list of routes has the shortest duration in time.
     * @param routes
     */
    private void setMinimumTime(List<Route> routes) {
        int totalTime = this.getTotalTime(routes);
        if (totalTime < minTime) {
            minTime = totalTime;
        }
    }

    /**
     * Calculates the time in minutes across all routes within a list.
     * @param routes
     * @return total time spent in all routes, in minutes.
     */
    private int getTotalTime(List<Route> routes) {
        int totalTime = 0;
        for (Route route : routes) {
            totalTime += TimeUtils.calculateTime(route.getDepartureTime(), route.getArrivalTime());
        }
        return totalTime;
    }


    private boolean validateIfDestinationWasFound(List<Route> routes, String destination) {
        return routes.stream().filter(route -> route.getDestination().equals(destination)).findAny().isPresent();
    }

    private boolean isNewDestination(List<Route> routes, String destination) {
        return !routes.stream().filter(route -> route.getCity().equals(destination)).findAny().isPresent();
    }


    public Itinerary getFallbackItinerary(String originCity, String destinationCity) {
        return new Itinerary()
                .setOrigin(originCity)
                .setDestination(destinationCity)
                .setShortestItinerary(new ArrayList<>())
                .setFastItinerary(new ArrayList<>());
    }
}

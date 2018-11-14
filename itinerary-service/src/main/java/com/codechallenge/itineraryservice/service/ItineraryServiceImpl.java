package com.codechallenge.itineraryservice.service;

import com.codechallenge.itineraryservice.dao.CityDao;
import com.codechallenge.itineraryservice.model.CityDTO;
import com.codechallenge.itineraryservice.model.Itinerary;
import com.codechallenge.itineraryservice.util.TimeUtils;
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
    CityDao cityDao;

    @NonNull
    private List<List<CityDTO>> validRoutes;

    private Integer minTime;


    public Itinerary getItinerary(String originCity, String destinationCity) {
        minTime = Integer.MAX_VALUE;

        this.findAllRoutes(originCity, destinationCity);
        log.info(validRoutes.size() + "Routes located! Filtering by connections and time");

        validRoutes.stream()
                .forEach(this::setMinimumTime);


        System.out.println(getShortestItinerary());
        log.info("fastest path in time: " + minTime + " minutes.");
        System.out.println(getFastestItinerary());

        return new Itinerary()
                .setDestination(destinationCity)
                .setOrigin(originCity)
                .setFastItinerary(getFastestItinerary())
                .setFewerConnectionsItinerary(getShortestItinerary());

//        return cityDao.getCityByName(originCity);

    }


    private List<List<CityDTO>> getFastestItinerary() {
        return validRoutes.stream()
                .filter(validRoute -> this.getTotalTime(validRoute) == minTime)
                .collect(Collectors.toList());

    }

    private List<List<CityDTO>> getShortestItinerary() {
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
        Optional<List<CityDTO>> firstDestinations = Optional.ofNullable(cityDao.getCityByName(originCity));

        log.debug("total located routes: " + firstDestinations.get().size());

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
    private void navigateThroughRoutes(List<CityDTO> routes, CityDTO currentRoute, String finalDestination) {
        Optional<List<CityDTO>> foundRoutes = Optional.ofNullable(cityDao.getCityByName(currentRoute.getDestination()));

        if (validateIfDestinationWasFound(foundRoutes.get(), finalDestination)) {
            //Final destination reached, adding the current path to the validRoutes list
            foundRoutes.get()
                    .stream()
                    .filter(route -> route.getDestination().equals(finalDestination))
                    .forEach(route -> {
                        List<CityDTO> finalRoute = new ArrayList<>();
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
                    List<CityDTO> newRoutes = new ArrayList<>();
                    newRoutes.addAll(routes);
                    newRoutes.add(route);
                    navigateThroughRoutes(newRoutes, route, finalDestination);
                });

    }


    //Minor helper functions just to keep the code cleaner

    private void setMinimumTime(List<CityDTO> routes) {
        int totalTime = 0;
        for (CityDTO route : routes) {
            totalTime += TimeUtils.calculateTime(route.getDepartureTime(), route.getArrivalTime());
        }

        if (totalTime < minTime) {
            minTime = totalTime;
        }
    }

    private int getTotalTime(List<CityDTO> routes) {
        int totalTime = 0;
        for (CityDTO route : routes) {
            totalTime += TimeUtils.calculateTime(route.getDepartureTime(), route.getArrivalTime());
        }
        return totalTime;
    }


    private boolean validateIfDestinationWasFound(List<CityDTO> routes, String destination) {
        return routes.stream().filter(route -> route.getDestination().equals(destination)).findAny().isPresent();
    }

    private boolean isNewDestination(List<CityDTO> routes, String destination) {
        return !routes.stream().filter(route -> route.getCity().equals(destination)).findAny().isPresent();
    }
}

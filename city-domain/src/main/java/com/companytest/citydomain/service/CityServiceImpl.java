package com.companytest.citydomain.service;

import com.companytest.citydomain.model.City;
import com.companytest.citydomain.repository.CityRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Override
    @HystrixCommand(fallbackMethod="getFallbackRoutes")
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    @HystrixCommand(fallbackMethod="getFilteredFallbackRoutes")
    public List<City> findByCityName(String cityName) {
        return cityRepository.findByCity(cityName);
    }

    @Override
    public void saveCity(List<City> city) {
        cityRepository.saveAll(city);
    }

    @Override
    public void deleteAll() {
        cityRepository.deleteAll();
    }


    //If the service fails, Hystrix will return a pre-defined list of routes.
    public List<City> getFallbackRoutes() {
        return generateFallbackRoutesList();
    }

    public List<City> getFilteredFallbackRoutes(String cityName) {
        return generateFallbackRoutesList()
                .stream()
                .filter(city -> city.getCity().equals(cityName))
                .collect(Collectors.toList());
    }


    private List<City> generateFallbackRoutesList() {
        List<City> routes = new ArrayList<>();

        routes.add(new City()
                .setCity("GRU")
                .setDestination("REC")
                .setDepartureTime("00:00")
                .setArrivalTime("07:00")
        );

        routes.add(new City()
                .setCity("SAO")
                .setDestination("REC")
                .setDepartureTime("00:00")
                .setArrivalTime("06:00")
        );

        routes.add(new City()
                .setCity("REC")
                .setDestination("GRU")
                .setDepartureTime("04:00")
                .setArrivalTime("06:00")
        );

        routes.add(new City()
                .setCity("REC")
                .setDestination("SAO")
                .setDepartureTime("04:00")
                .setArrivalTime("06:00")
        );

        return routes;
    }

}

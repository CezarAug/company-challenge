package com.codechallenge.itineraryservice.dao;

import com.codechallenge.itineraryservice.model.Route;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient("CITY-DOMAIN")
public interface RouteDao {

    @RequestMapping(value = "/route/{cityName}", method = RequestMethod.GET)
    List<Route> getCityByName(@PathVariable("cityName") String cityName);
}

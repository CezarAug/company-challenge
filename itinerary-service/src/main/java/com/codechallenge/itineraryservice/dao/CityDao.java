package com.codechallenge.itineraryservice.dao;

import com.codechallenge.itineraryservice.model.CityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient("CITY-DOMAIN")
public interface CityDao {

    @GetMapping("/city")
    List<CityDTO> getCity();

    @RequestMapping(value = "/city/{cityName}", method = RequestMethod.GET)
    List<CityDTO> getCityByName(@PathVariable("cityName") String cityName);


}

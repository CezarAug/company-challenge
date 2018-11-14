package com.companytest.citydomain.service;

import com.companytest.citydomain.model.City;

import java.util.List;

public interface CityService {
    List<City> findAll();
    List<City> findByCityName(String cityName);
    void saveCity(List<City> city);
    void deleteAll();
}

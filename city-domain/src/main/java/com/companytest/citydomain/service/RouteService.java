package com.companytest.citydomain.service;

import com.companytest.citydomain.model.Route;

import java.util.List;

public interface RouteService {
    List<Route> findAll();
    List<Route> findByCityName(String cityName);
    void saveCity(List<Route> route);
    void deleteAll();
}

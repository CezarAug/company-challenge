package com.companytest.citydomain.repository;

import com.companytest.citydomain.model.Route;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Extension from the default mongo repository just to implement find by city
 */
public interface RouteRepository extends MongoRepository<Route, String> {

    @Cacheable("route")
    List<Route> findByCity(String city);

}

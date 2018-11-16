package com.companytest.citydomain.repository;

import com.companytest.citydomain.model.City;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CityRepository extends MongoRepository<City, String> {

    @Cacheable("city")
    List<City> findByCity(String city);

}

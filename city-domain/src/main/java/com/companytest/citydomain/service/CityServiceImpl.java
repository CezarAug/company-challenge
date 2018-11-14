package com.companytest.citydomain.service;

import com.companytest.citydomain.model.City;
import com.companytest.citydomain.repository.CityRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@NoArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
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


}

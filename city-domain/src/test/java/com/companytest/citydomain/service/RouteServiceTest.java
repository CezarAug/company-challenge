package com.companytest.citydomain.service;

import com.companytest.citydomain.repository.RouteRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static com.companytest.citydomain.util.TestUtils.generateFilteredMockRoutesList;
import static com.companytest.citydomain.util.TestUtils.generateMockRoutesList;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteServiceImpl routeService;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void shouldReturnAllRoutes() {
        Mockito.when(routeRepository.findAll()).thenReturn(generateMockRoutesList());
        Assert.assertEquals(generateMockRoutesList(), routeService.findAll());
    }

    @Test
    public void shouldReturnOnlyRequiredCityRoutes() {
        final String city = "SAO";
        Mockito.when(routeRepository.findByCity(city)).thenReturn(generateFilteredMockRoutesList(city));
        Assert.assertEquals(generateFilteredMockRoutesList(city), routeService.findByCityName(city));
    }

    @Test
    public void shouldReturnAllRoutes_hystrix() {
        Assert.assertEquals(generateMockRoutesList(), routeService.getFallbackRoutes());
    }

    @Test
    public void shouldReturnOnlyRequiredCityRoutes_Hystrix() {
        final String city = "SAO";
        Assert.assertEquals(generateFilteredMockRoutesList(city), routeService.getFilteredFallbackRoutes(city));
    }

    //Save and delete should just not fail. As they are just helper functions for testing and populating data in this demo application

    @Test
    public void shouldSaveRoutes() {
        Mockito.when(routeRepository.saveAll(any())).thenReturn(null);
        routeRepository.saveAll(null);
    }


}

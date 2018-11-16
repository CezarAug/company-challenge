package com.codechallenge.itineraryservice.service;

import com.codechallenge.itineraryservice.dao.RouteDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.codechallenge.itineraryservice.util.TestUtils.generateFilteredMockRoutesList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItineraryServiceTest {

    @Mock
    private RouteDao routeDao;

    @InjectMocks
    private ItineraryServiceImpl itineraryService;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(routeDao.getCityByName("SAO")).thenReturn((generateFilteredMockRoutesList("SAO")));
        Mockito.when(routeDao.getCityByName("GRU")).thenReturn((generateFilteredMockRoutesList("GRU")));
        Mockito.when(routeDao.getCityByName("REC")).thenReturn((generateFilteredMockRoutesList("REC")));
        Mockito.when(routeDao.getCityByName("CGH")).thenReturn((generateFilteredMockRoutesList("CGH")));


    }

    @Test
    public void shouldReturnAllRoutes() {
        Assert.assertEquals("SAO", itineraryService.getItinerary("SAO", "REC").getOrigin());
        Assert.assertEquals("REC", itineraryService.getItinerary("SAO", "REC").getDestination());
        Assert.assertEquals(generateFilteredMockRoutesList("SAO"), itineraryService.getItinerary("SAO", "REC").getShortestItinerary().get(0));
        Assert.assertEquals(generateFilteredMockRoutesList("SAO"), itineraryService.getItinerary("SAO", "REC").getFastItinerary().get(0));
    }

    @Test
    public void shouldReturnAllRoutes_withMultipleIteractions() {
        Assert.assertEquals("GRU", itineraryService.getItinerary("GRU", "SAO").getOrigin());
        Assert.assertEquals("SAO", itineraryService.getItinerary("GRU", "SAO").getDestination());
        Assert.assertEquals(2, itineraryService.getItinerary("GRU", "SAO").getShortestItinerary().get(0).size());
        Assert.assertEquals(2, itineraryService.getItinerary("GRU", "SAO").getFastItinerary().get(0).size());
    }

}

package com.codechallenge.itineraryservice.controller;


import com.codechallenge.itineraryservice.model.Itinerary;
import com.codechallenge.itineraryservice.service.ItineraryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static com.codechallenge.itineraryservice.util.TestUtils.generateFilteredMockRoutesList;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ItineraryControllerTest {

    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Autowired
    ItineraryController itineraryController;

    @MockBean
    ItineraryService itineraryService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();

        Mockito.when(itineraryService.getItinerary(any(), any())).thenReturn(
                new Itinerary()
                    .setOrigin("Origin")
                .setDestination("Destination")
                .setFastItinerary(Arrays.asList(generateFilteredMockRoutesList("GRU")))
                .setShortestItinerary(Arrays.asList(generateFilteredMockRoutesList("GRU")))
        );
    }

    @Test
    public void getOneCity_shouldReturnAllRoutesFiltered() throws Exception {
        this.mockMvc
                .perform(get("/itinerary-test/SAO/GRU"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.origin").value("Origin"))
                .andExpect(jsonPath("$.destination").value("Destination"))
                .andExpect(jsonPath("$.shortestItinerary.[0].[0].city").value("GRU"))
                .andExpect(jsonPath("$.fastItinerary.[0].[0].city").value("GRU"));
    }
}

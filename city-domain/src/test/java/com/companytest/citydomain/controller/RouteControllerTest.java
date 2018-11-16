package com.companytest.citydomain.controller;

import com.companytest.citydomain.service.RouteService;
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

import static com.companytest.citydomain.util.TestUtils.generateFilteredMockRoutesList;
import static com.companytest.citydomain.util.TestUtils.generateMockRoutesList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RouteControllerTest {

    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Autowired
    RouteController routeController;

    @MockBean
    RouteService routeService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();

        Mockito.when(routeService.findAll()).thenReturn(generateMockRoutesList());
        Mockito.when(routeService.findByCityName("SAO")).thenReturn(generateFilteredMockRoutesList("SAO"));

    }

    @Test
    public void getAllCities_shouldReturnAllRoutes() throws Exception {
        this.mockMvc
                .perform(get("/route-test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$[0].city").value("GRU"));
    }


    @Test
    public void getOneCity_shouldReturnAllRoutesFiltered() throws Exception {
        this.mockMvc
                .perform(get("/route-test/SAO"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$[0].city").value("SAO"))
                .andExpect(jsonPath("$[0].destination").value("REC"));
    }
}

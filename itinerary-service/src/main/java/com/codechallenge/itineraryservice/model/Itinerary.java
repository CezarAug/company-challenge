package com.codechallenge.itineraryservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Itinerary {

    private String origin;
    private String destination;

    private List<List<CityDTO>> fewerConnectionsItinerary;
    private List<List<CityDTO>> fastItinerary;
}

package com.codechallenge.itineraryservice.service;

import com.codechallenge.itineraryservice.model.Itinerary;

public interface ItineraryService {

    Itinerary getItinerary(String originCity, String destinationCity);
}

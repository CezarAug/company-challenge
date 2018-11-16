package com.codechallenge.itineraryservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@ApiModel
public class Itinerary {

    @ApiModelProperty(example = "GRU - Guarulhos", name = "Departure city")
    private String origin;
    @ApiModelProperty(example = "REC - Recife", name = "Destination city")
    private String destination;

    @ApiModelProperty(name = "Shortest routes list.")
    private List<List<Route>> shortestItinerary;
    @ApiModelProperty(name = "Fastest routes list.")
    private List<List<Route>> fastItinerary;
}

package com.codechallenge.itineraryservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Route model class
 *
 * @Author Cezar Augusto
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@ApiModel
@Accessors(chain = true)
public class Route {

    @ApiModelProperty(example = "GRU - Guarulhos", name = "Departure city")
    @NotNull
    private String city;

    @ApiModelProperty(example = "REC - Recife", name = "Destination city")
    @NotNull
    private String destination;

    @ApiModelProperty(example = "00:00", name = "Departure time")
    @NotNull
    private String departureTime;

    @ApiModelProperty(example = "03:00", name = "Arrival time")
    @NotNull
    private String arrivalTime;


}

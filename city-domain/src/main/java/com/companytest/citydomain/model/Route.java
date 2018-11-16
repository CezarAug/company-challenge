package com.companytest.citydomain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

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
@ApiModel(description = "Defines a route between two cities.")
@Accessors(chain = true)
public class Route {

    @Id
    @JsonIgnore
    private String id;

    @ApiModelProperty(example = "GRU - Guarulhos", name = "Departure city", required =  true)
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

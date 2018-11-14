package com.companytest.citydomain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

/**
 * City model class
 *
 * @Author Cezar Augusto
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@ApiModel
public class City {

    @Id
    @JsonIgnore
    private String id;

    @ApiModelProperty(value = "GRU - Guarulhos", name = "Departure city")
    @NotNull
    private String city;

    @ApiModelProperty(value = "REC - Recife", name = "Destination city")
    @NotNull
    private String destination;

    @ApiModelProperty(value = "00:00", name = "Departure time")
    @NotNull
    private String departureTime;

    @ApiModelProperty(value = "03:00", name = "Arrival time")
    @NotNull
    private String arrivalTime;


}

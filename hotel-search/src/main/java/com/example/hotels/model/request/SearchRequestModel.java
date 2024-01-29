package com.example.hotels.model.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

/**
 * @author Osada
 * @Date 1/29/2024
 */
@Data
public class SearchRequestModel {

    @Parameter(description = "Page number", example= "1", required = false)
    private Integer page = 1;

    @Parameter(description = "Page size", example = "10", required = false)
    private Integer size = 10;

    @Parameter(description = "Hotel name", required = false)
    private String hotel;

    @Parameter(description = "City name", required = true)
    private String city;

    @Parameter(description = "Latitude", required = false)
    private Float lat;

    @Parameter(description = "Longitude", required = false)
    private Float lng;

    @Parameter(description = "Free places", required = false)
    private Boolean fpn;

    @Parameter(description = "Hotel age", required = false)
    private Integer age;

}

package com.example.hotels.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Osada
 * @Date 1/29/2024
 */
@NoArgsConstructor
@Data
public class SearchResponseModel implements Serializable{

    @Schema(description = "Hotel name.",
            example = "Royal Beach", required = true)
    private String hotel;

    @Schema(description = "City name where hotel is located at English.",
            example = "London", required = true)
    private String city;

    @Schema(description = "Response status.",
            example = "200", required = false)
    private Integer status = 200;

    @Schema(description = "Error text.",
            example = "Exception", required = false)
    private String error = "";

    public SearchResponseModel(String hotel, String city) {
        this.hotel = hotel;
        this.city = city;
    }
}

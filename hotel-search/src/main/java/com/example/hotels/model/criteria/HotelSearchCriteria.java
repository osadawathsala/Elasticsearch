package com.example.hotels.model.criteria;

import lombok.Data;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * @author Osada
 * @Date 1/29/2024
 */
@Data
public class HotelSearchCriteria {

    public static final int SIZE_MIN = 1;
    public static final int SIZE_MAX = 20;

    private Integer page = 1;
    private Integer size = 10;
    private Boolean freePlacesAtNow = false;
    private String hotelName;
    private String cityName;
    private Integer hotelAge;
    private Integer hotelStars;
    private GeoPoint geoCoordinates;
}

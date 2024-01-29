package com.example.hotels.dependencies.hotel_search.filters;

import co.elastic.clients.elasticsearch._types.GeoLocation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import com.example.hotels.model.criteria.HotelSearchCriteria;

import co.elastic.clients.elasticsearch._types.query_dsl.GeoDistanceQuery;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * @author Osada
 * @Date 1/29/2024
 */
public class GeoDistanceFilter {

    public static Query createFilter(HotelSearchCriteria criteria) {

        GeoPoint coordinates = criteria.getGeoCoordinates();

        GeoLocation location = new GeoLocation.Builder()
                .latlon(latlon -> latlon.lat(coordinates.getLat()).lon(coordinates.getLon()))
                .build();

        QueryVariant geoDistanceQuery = new GeoDistanceQuery.Builder()
                .field("location")
                .location(location)
                .distance("30km")
                .build();

        return new Query(geoDistanceQuery);
    }
}

package com.example.hotels.dependencies.hotel_search.filters;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;
import com.example.hotels.model.criteria.HotelSearchCriteria;

/**
 * @author Osada
 * @Date 1/29/2024
 */
public class HotelAgeFilter {

    public static Query createFilter(HotelSearchCriteria criteria){

        QueryVariant rangeAgeQuery = new RangeQuery.Builder()
                .field("name")
                .gte(JsonData.of(criteria.getHotelAge()))
                .build();

        return new Query(rangeAgeQuery);

    }
}

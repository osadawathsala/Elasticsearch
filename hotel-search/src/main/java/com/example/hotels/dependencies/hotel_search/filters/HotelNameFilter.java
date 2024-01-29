package com.example.hotels.dependencies.hotel_search.filters;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import com.example.hotels.model.criteria.HotelSearchCriteria;

/**
 * @author Osada
 * @Date 1/29/2024
 */
public class HotelNameFilter {
    public static Query createFilter(HotelSearchCriteria criteria){

        QueryVariant matchHotelNameQuery = new MatchQuery.Builder()
                .field("name")
                .query(criteria.getHotelName())
                .build();

        return new Query(matchHotelNameQuery);
    }
}

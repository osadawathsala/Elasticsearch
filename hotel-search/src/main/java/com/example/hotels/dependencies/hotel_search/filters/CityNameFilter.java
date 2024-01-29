package com.example.hotels.dependencies.hotel_search.filters;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import com.example.hotels.model.criteria.HotelSearchCriteria;

/**
 * @author Osada
 * @Date 1/29/2024
 */

public class CityNameFilter {
    public static Query createFilter(HotelSearchCriteria criteria){

        QueryVariant matchQueryFirst =new MatchQuery.Builder()
                .field("cityNameEn")
                .query(criteria.getCityName())
                .fuzziness("2")
                .build();

        QueryVariant matchQuerySecond = new MatchQuery.Builder()
                .field("cityNameEn")
                .query("London")
                .build();

        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        boolQueryBuilder.should(new Query(matchQueryFirst));
        boolQueryBuilder.should(new Query(matchQuerySecond));

        return new Query(boolQueryBuilder.build());
    }
}

package com.example.hotels.dependencies.hotel_search;

import com.example.hotels.model.criteria.HotelSearchCriteria;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

/**
 * @author Osada
 * @Date 1/29/2024
 */

public interface QueryBuilderInterface {
    void createQuery(HotelSearchCriteria criteria);
    NativeQuery getSearch();
}

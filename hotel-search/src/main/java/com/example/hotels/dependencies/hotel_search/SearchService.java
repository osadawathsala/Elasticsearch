package com.example.hotels.dependencies.hotel_search;

import com.example.hotels.model.criteria.HotelSearchCriteria;
import com.example.hotels.model.elasticsearch.HotelBookingDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Component;

/**
 * @author Osada
 * @Date 1/29/2024
 */

@Component
public class SearchService {

    @Autowired
    QueryBuilder queryBuilder;

    @Autowired
    ElasticsearchOperations operations;

    public SearchPage search(HotelSearchCriteria criteria) {

        queryBuilder.createQuery(criteria);
        NativeQuery search = queryBuilder.getSearch();

        SearchHits<HotelBookingDocument> searchHits = operations.search(
                search,
                HotelBookingDocument.class
        );

        SearchPage<HotelBookingDocument> searchPage = SearchHitSupport.searchPageFor(
                searchHits,
                queryBuilder.getPageRequest()
        );

        return searchPage;
    }

    public String getRawJsonQuery(HotelSearchCriteria criteria) {

        queryBuilder.createQuery(criteria);
        NativeQuery search = queryBuilder.getSearch();

        return search.getQuery().toString();
    }

}

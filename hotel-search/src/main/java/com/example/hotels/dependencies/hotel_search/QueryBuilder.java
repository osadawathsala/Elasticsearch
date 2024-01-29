package com.example.hotels.dependencies.hotel_search;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.example.hotels.model.criteria.HotelSearchCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.stereotype.Component;

import com.example.hotels.dependencies.hotel_search.filters.*;

/**
 * @author Osada
 * @Date 1/29/2024
 */
@Component
public class QueryBuilder implements QueryBuilderInterface{

    private NativeQueryBuilder searchQueryBuilder;
    private PageRequest pageRequest;

    public QueryBuilder() {
        this.searchQueryBuilder = new NativeQueryBuilder();
    }

    @Override
    public void createQuery(HotelSearchCriteria criteria) {
        this.setPageOffset(criteria);
        this.setFilters(criteria);
        this.setAggregation(criteria);
        this.setSorting(criteria);
        this.setFields(criteria);
    }

    @Override
    public NativeQuery getSearch() {
        return this.searchQueryBuilder.build();
    }

    public PageRequest getPageRequest(){
        return this.pageRequest;
    }

    private void setFilters(HotelSearchCriteria criteria) {

        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

        if (!criteria.getHotelName().isEmpty()) {
            boolQueryBuilder.must(HotelNameFilter.createFilter(criteria));
        }

        if (!criteria.getCityName().isEmpty()) {
            boolQueryBuilder.must(CityNameFilter.createFilter(criteria));
        }

        if (criteria.getHotelAge() >= 0) {
            boolQueryBuilder.should(HotelAgeFilter.createFilter(criteria));
        }

        boolQueryBuilder.filter(GeoDistanceFilter.createFilter(criteria));

        this.searchQueryBuilder.withQuery(q -> q
                .bool(boolQueryBuilder.build())
        );
    }

    private void setPageOffset(HotelSearchCriteria criteria) {
        this.pageRequest = PageRequest.of(
                criteria.getPage(),
                criteria.getSize()
        );
        this.searchQueryBuilder.withPageable(this.pageRequest);
    }

    private void setFields(HotelSearchCriteria criteria) {
        //choose fields you want to get from ElasticSearch
    }

    private void setSorting(HotelSearchCriteria criteria) {
        //add sorting
    }

    private void setAggregation(HotelSearchCriteria criteria) {
        //add aggregations
    }
}

package com.example.hotels.dependencies.hotel_search_criteria;

import com.example.hotels.model.criteria.HotelSearchCriteria;

/**
 * @author Osada
 * @Date 1/29/2024
 */
abstract class AbstractCriteriaDirector {
    protected AbstractCriteriaBuilder builder;

    public AbstractCriteriaDirector(AbstractCriteriaBuilder builder) {
        this.builder = builder;
    }

    abstract void buildCriteria();

    abstract HotelSearchCriteria getCriteria();


}

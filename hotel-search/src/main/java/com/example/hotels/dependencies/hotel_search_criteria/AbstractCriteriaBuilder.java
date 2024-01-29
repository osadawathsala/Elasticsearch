package com.example.hotels.dependencies.hotel_search_criteria;

import com.example.hotels.model.criteria.HotelSearchCriteria;

/**
 * @author Osada
 * @Date 1/29/2024
 */
abstract class AbstractCriteriaBuilder {
    abstract HotelSearchCriteria getCriteria();
    abstract void createCriteria();
}

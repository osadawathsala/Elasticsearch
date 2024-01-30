package com.example.hotels.dependencies.hotel_search_criteria;

import com.example.hotels.model.criteria.HotelSearchCriteria;

/**
 * @author Osada
 * @Date 1/29/2024
 */
public class HotelSearchCriteriaDirector extends AbstractCriteriaDirector{

    public HotelSearchCriteriaDirector(AbstractCriteriaBuilder builder) {
        super(builder);
    }

    @Override
    public void buildCriteria() {
        this.builder.createCriteria();
    }

    @Override
    public HotelSearchCriteria getCriteria() {
        return this.builder.getCriteria();
    }
}

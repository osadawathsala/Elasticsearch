package com.example.hotels.dependencies.hotel_search_criteria;

import com.example.hotels.model.criteria.HotelSearchCriteria;
import com.example.hotels.model.request.SearchRequestModel;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * @author Osada
 * @Date 1/29/2024
 */
public class HotelSearchCriteriaUrlBuilder extends AbstractCriteriaBuilder{

    private SearchRequestModel searchRequestModel;
    private HotelSearchCriteria hotelSearchCriteria;

    public HotelSearchCriteriaUrlBuilder(SearchRequestModel searchRequestModel, HotelSearchCriteria hotelSearchCriteria) {
        this.searchRequestModel = searchRequestModel;
        this.hotelSearchCriteria = hotelSearchCriteria;
    }

    @Override
    HotelSearchCriteria getCriteria() {
        return this.hotelSearchCriteria;
    }

    @Override
    void createCriteria() {

        if (searchRequestModel.getPage() >= 1) {
            this.hotelSearchCriteria.setPage(searchRequestModel.getPage() - 1);
        }

        this.hotelSearchCriteria.setHotelAge(searchRequestModel.getAge());
        this.hotelSearchCriteria.setCityName(searchRequestModel.getCity());
        this.hotelSearchCriteria.setHotelName(searchRequestModel.getHotel());
        this.hotelSearchCriteria.setFreePlacesAtNow(searchRequestModel.getFpn());

        if (searchRequestModel.getSize() <= HotelSearchCriteria.SIZE_MAX &&
                searchRequestModel.getSize() >= HotelSearchCriteria.SIZE_MIN
        ) {
            this.hotelSearchCriteria.setSize(searchRequestModel.getSize());
        }

        if (searchRequestModel.getLat() != null && searchRequestModel.getLng() != null) {
            hotelSearchCriteria.setGeoCoordinates(new GeoPoint(
                    searchRequestModel.getLat(),
                    searchRequestModel.getLng()
            ));
        }
    }
}

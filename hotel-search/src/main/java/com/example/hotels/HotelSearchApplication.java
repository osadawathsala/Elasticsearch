package com.example.hotels;

import com.example.hotels.dependencies.hotel_search.SearchService;
import com.example.hotels.dependencies.hotel_search_criteria.HotelSearchCriteriaDirector;
import com.example.hotels.dependencies.hotel_search_criteria.HotelSearchCriteriaUrlBuilder;
import com.example.hotels.model.criteria.HotelSearchCriteria;
import com.example.hotels.model.request.SearchRequestModel;
import com.example.hotels.model.response.SearchResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.hotels.model.elasticsearch.HotelBookingDocument;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootApplication
@RestController
public class HotelSearchApplication {

	@Autowired
	SearchService searchService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<List<SearchResponseModel>> search(
			@Validated @ModelAttribute SearchRequestModel searchRequestModel
	) {

		try {

			HotelSearchCriteriaUrlBuilder builder = new HotelSearchCriteriaUrlBuilder(searchRequestModel);
			HotelSearchCriteriaDirector director = new HotelSearchCriteriaDirector(builder);
			director.buildCriteria();
			HotelSearchCriteria criteria = director.getCriteria();

			SearchPage<HotelBookingDocument> searchPage = searchService.search(criteria);

			Iterator<SearchHit<HotelBookingDocument>> iterator = searchPage.iterator();
			List<SearchResponseModel> results = new ArrayList<>();

			while (iterator.hasNext()) {

				HotelBookingDocument hotel = iterator.next().getContent();
				results.add(new SearchResponseModel(hotel.getName(),hotel.getCityNameEn()));

			}

			return ResponseEntity.ok(results);

		} catch (Throwable t) {

			List<SearchResponseModel> results = new ArrayList<>();
			SearchResponseModel response = new SearchResponseModel("", "");
			response.setError(t.getMessage());
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			results.add(response);

			return ResponseEntity.ok(results);
		}
	}

	@RequestMapping(value = "/show-raw-json", method = RequestMethod.GET)
	public String showRawJson (@ModelAttribute SearchRequestModel searchRequestModel) {

		try {

			HotelSearchCriteriaUrlBuilder builder = new HotelSearchCriteriaUrlBuilder(searchRequestModel);
			HotelSearchCriteriaDirector director = new HotelSearchCriteriaDirector(builder);
			director.buildCriteria();
			HotelSearchCriteria criteria = director.getCriteria();

			String rawJsonQuery = searchService.getRawJsonQuery(criteria);

			return rawJsonQuery;

		} catch (Throwable t) {

			// https://reflectoring.io/spring-boot-exception-handling/
			// for help
			return t.getMessage();
		}
	}

	@RequestMapping(value = "/show-criteria", method = RequestMethod.GET)
	public String showCriteria(@ModelAttribute SearchRequestModel searchRequestModel) {

		try {

		HotelSearchCriteriaUrlBuilder builder = new HotelSearchCriteriaUrlBuilder(searchRequestModel);
		HotelSearchCriteriaDirector director = new HotelSearchCriteriaDirector(builder);
    	director.buildCriteria();
		HotelSearchCriteria criteria = director.getCriteria();

		String criteriaParameters = String.valueOf(
				"coordinates: " + criteria.getGeoCoordinates() + "\n" +
						"hotel name: " + criteria.getHotelName() + "\n" +
						"city name: " + criteria.getCityName() + "\n" +
						"age: " + criteria.getHotelAge() + "\n" +
						"free: " + criteria.getFreePlacesAtNow() + "\n" +
						"star: " + criteria.getHotelStars() + "\n" +
						"size: " + criteria.getSize() + "\n" +
						"page: " + criteria.getPage()
		);

		return criteriaParameters;

		} catch (Throwable t) {
			// https://reflectoring.io/spring-boot-exception-handling/
			return t.getMessage();
		}
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		try {
			return String.valueOf("Test");
		} catch (Throwable t) {
			return t.getMessage();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(HotelSearchApplication.class, args);
	}

}

package com.example.elasticsearch;

import com.example.elasticsearch.models.elasticsearch.Comment;
import com.example.elasticsearch.models.elasticsearch.HotelBookingDocument;
import com.example.elasticsearch.repositories.HotelBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.data.elasticsearch.core.join.JoinField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Arrays;

/**
 * @author Osada
 * @Date1 1/21/2024
 */

@Component
@Order(1)
public class SpringBootEsInitLoader implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(SpringBootEsInitLoader.class);

    //defines whether to index test data or not
    private Boolean loadInitData;

    @Autowired
    private HotelBookingRepository hotelBookingRepository;
    @Override
    public void run(String... args) throws Exception {

        logger.info("CommandLineRunner#run(String[])");

        try {

            if (loadInitData) {

            HotelBookingDocument goldenHotel = new HotelBookingDocument();
            goldenHotel.setId("1");
            goldenHotel.setHotelID(1);
            goldenHotel.setName("Golden star hotel");
            goldenHotel.setCityNameEn("Warsaw");
            goldenHotel.setLocation(new GeoPoint(52.21, 21.01));
            goldenHotel.setAge(7);
            goldenHotel.setStarts(5);
            goldenHotel.setFreePlacesAtNow(true);
            goldenHotel.setRating(4.85);
            goldenHotel.setRelation(new JoinField<>("hotel"));

            Comment comment = new Comment();
            comment.setContent("Some content");
            comment.setCreatedDate(Instant.now());
            comment.setHotelID(1);
            comment.setStarts(5);
            goldenHotel.setComments(Arrays.asList(comment));

            hotelBookingRepository.save(goldenHotel);

            HotelBookingDocument booking = new HotelBookingDocument();
            booking.setId("2");
            booking.setPrice(100.00);
            booking.setCreatedDate(Instant.now());
            booking.setRelation(new JoinField<>("booking", goldenHotel.getId()));

            hotelBookingRepository.save(booking);

            HotelBookingDocument silverHotel = new HotelBookingDocument();
            silverHotel.setId("3");
            silverHotel.setHotelID(3);
            silverHotel.setName("Silver star hotel");
            silverHotel.setCityNameEn("Warsaw");
            silverHotel.setLocation(new GeoPoint(52.13, 21.01));
            silverHotel.setAge(7);
            silverHotel.setStarts(4);
            silverHotel.setFreePlacesAtNow(true);
            silverHotel.setRating(4.6);
            silverHotel.setRelation(new JoinField<>("hotel"));

            hotelBookingRepository.save(silverHotel);

            }
        } catch (Throwable t) {
            logger.error(t.toString());
        }

        if (args.length > 0) {
            logger.info("first command line parameter: '{}'", args[0]);
        }
    }
}

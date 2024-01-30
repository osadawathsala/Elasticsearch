package com.example.hotels.repository;

import com.example.hotels.model.elasticsearch.HotelBookingDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Osada
 * @Date 1/30/2024
 */
public interface HotelBookingRepository extends ElasticsearchRepository<HotelBookingDocument,String>
{
    List<HotelBookingDocument> findByName(String name);
}

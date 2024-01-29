package com.example.elasticsearch.repositories;

import com.example.elasticsearch.models.elasticsearch.HotelBookingDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Osada
 * @Date 1/21/2024
 */
public interface HotelBookingRepository extends ElasticsearchRepository<HotelBookingDocument,String> {
}

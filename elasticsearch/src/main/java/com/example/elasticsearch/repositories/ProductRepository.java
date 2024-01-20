package com.example.elasticsearch.repositories;

import com.example.elasticsearch.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Osada
 * @Date 1/20/2024
 */
public interface ProductRepository extends ElasticsearchRepository<Product,String> {
    List<Product> findByName(String name);

    List<Product> findByNameContaining(String name);

    List<Product> findByManufacturerAndCategory
            (String manufacturer, String category);

    Page<Product> findByManufacturer(String manufacturer, Pageable pageable);

}

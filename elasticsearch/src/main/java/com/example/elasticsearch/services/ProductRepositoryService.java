package com.example.elasticsearch.services;

import com.example.elasticsearch.models.Product;
import com.example.elasticsearch.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;

/**
 * @author Osada
 * @Date 1/20/2024
 */
@Service
public class ProductRepositoryService {
    @Autowired
    ProductRepository productRepository;

    public void createProductIndexBulk(final List<Product> products) {
        Iterable<Product> results = productRepository.saveAll(products);
    }

    /**
     *
     * @param product
     */
    public void createProductIndex(final Product product) {
        productRepository.save(product);
    }

    public List<Product> findProductsByManufacturerAndCategory(final String manufacturer, final String category) {
        return productRepository.findByManufacturerAndCategory(manufacturer, category);
    }

    public List<Product> findByProductName(final String productName) {
        return productRepository.findByName(productName);
    }

    public List<Product> findByProductMatchingNames(final String productName) {
        return productRepository.findByNameContaining(productName);
    }

    public Iterable<Product> findAllProducts(){
        return productRepository.findAll();
    }

    List<Product> findByManufacturer(String manufacturer){
        Pageable pageable = PageRequest.of(0,10, Sort.by("price"));
        return productRepository.findByManufacturer(manufacturer,pageable).getContent();
    }
}

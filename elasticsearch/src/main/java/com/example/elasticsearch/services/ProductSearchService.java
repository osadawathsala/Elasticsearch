package com.example.elasticsearch.services;

import com.example.elasticsearch.models.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsIterator;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;


import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Osada
 * @Date 1/20/2024
 * NativeQuery
 * StringQuery
 * CriteriaQuery
 */
@Service
public class ProductSearchService {

    private static final String PRODUCT_INDEX = "productindex";

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public Iterable<Product> createProductIndexBulk(final List<Product> products) {
        return elasticsearchOperations.save(products);
    }

    public List<Product> searchProductWithPriceBetween(long startingSalary, long endingSalary) {

        Criteria criteria = new Criteria("price")
                .greaterThan(startingSalary)
                .lessThan(endingSalary);

        Query query = new CriteriaQuery(criteria);
        SearchHits<Product> searchHits = elasticsearchOperations.search(query, Product.class);

        List<Product> productList = searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();

        return productList;
    }

    public List<Product> getAllProductByBrandName(final String brandName){

        Query query = NativeQuery.builder()
                .withQuery(q -> q
                .match(m -> m
                        .field("manufacturer")
                        .query(brandName)
                )
        ).build();

        SearchHits<Product> searchHits = elasticsearchOperations.search(query, Product.class);

        List<Product> productList = searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();

        return productList;

    }

    public List<Product> searchStringQuery(final String productName){
        Query query = new StringQuery("{ \"match\": { \"name\": { \"query\": \"" + productName + "\" } } } ");
        SearchHits<Product> searchHits = elasticsearchOperations.search(query, Product.class);

        List<Product> productList = searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();

        return productList;
    }

    public List<Product> getEmployeeUsingScroll(final String brandName){

        Query searchQuery = NativeQuery.builder()
                .withQuery(q -> q
                        .match(m -> m
                                .field("manufacturer")
                                .query(brandName)
                        )
                )
                .withPageable(PageRequest.of(0, 10))
                .build();

        SearchHitsIterator<Product> stream = elasticsearchOperations.searchForStream(searchQuery, Product.class);

        List<Product> products = new ArrayList<>();
        while (stream.hasNext()) {
            products.add(stream.next().getContent());
        }

        stream.close();

        return products;

    }


}

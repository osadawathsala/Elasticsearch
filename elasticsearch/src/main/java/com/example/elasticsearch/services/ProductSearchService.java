package com.example.elasticsearch.services;

import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import com.example.elasticsearch.Operations;
import com.example.elasticsearch.models.Product;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHitsIterator;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;


import org.springframework.data.elasticsearch.core.SearchHit;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Osada
 * @Date 1/20/2024
 * NativeQuery
 * StringQuery
 * CriteriaQuery
 */
@Service
@Slf4j
public class ProductSearchService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    
    /**
     * Persist the individual Product entity in the Elasticsearch cluster
     */
    public void  saveIndex(Product product) {
        Product saveProduct = elasticsearchOperations.save(product);
    }
    /**
     * Bulk-save the products in the Elasticsearch cluster
     */
    public List<String> saveIndexBulk(final List<Product> products) {
        List<String> productIds = new ArrayList<>();
        elasticsearchOperations.save(products).forEach(product -> productIds.add(product.getId()));
        return productIds;
    }

    public List<Product> searchByProductName(String productName){
        // Get all item with given name
        Criteria criteria = new Criteria("name").is(productName);
        Query searchQuery = new CriteriaQuery(criteria);
        SearchHits<Product> searchHits =elasticsearchOperations
                .search(searchQuery, Product.class);

        List<Product> productList = searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();

        return productList;
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

    public List<Product> processSearch(final String query) {

        log.info("Search with query {}", query);

        Supplier<co.elastic.clients.elasticsearch._types.query_dsl.Query> supplierQuery = Operations
                .searchQuery(query,List.of("name","description"));

//        Query searchQuery2 = NativeQuery.builder()
//                .withQuery(q -> q.multiMatch(m-> m.fields(List.of("name","description")).query(query).fuzziness(Fuzziness.AUTO.asString())))
//                .build();


//                Query searchQuery2 = NativeQuery.builder()
//                .withQuery(q -> q.multiMatch(m-> m.fields(List.of("name","description"))
//                        .query(query)
//                        .fuzziness(Fuzziness.AUTO.asString())))
//                .build();


        Query searchQuery = NativeQuery.builder()
               .withQuery(supplierQuery.get()).build();

        SearchHits<Product> searchHits = elasticsearchOperations.search(searchQuery, Product.class);

        List<Product> productList = searchHits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();

        //elasticsearchOperations.search();
//        Query searchQuery = new StringQuery("{\"multi_match\": {\"fields\":  [ \"name\", \"description\"],\"query\":\"" + query + "\",\"fuzziness\": \"AUTO\"}}");
//        SearchHits<Product> searchHits = elasticsearchOperations.search(searchQuery, Product.class);
//        List<Product> productList = searchHits.getSearchHits()
//                .stream()
//                .map(SearchHit::getContent)
//                .toList();

        return productList;
    }


}

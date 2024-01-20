package com.example.elasticsearch.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author Osada
 * @Date 1/20/2024
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.elasticsearch")
public class ElasticsearchClientConfig extends ElasticsearchConfiguration {
    /**
     using ElasticsearchConfiguration,we can inject the following beans into other Spring components
     ElasticsearchOperations;
     ElasticsearchClient;
     RestClient;
     using ReactiveElasticsearchConfiguration we can inject
     ReactiveElasticsearchOperations operations;
     ReactiveElasticsearchClient elasticsearchClient;
     RestClient restClient;

     */
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
    }
}

package com.example.elasticsearch.config;

import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;

/**
 * @author Osada
 * @Date 1/20/2024
 */
//@Configuration
//@EnableReactiveElasticsearchRepositories
public class ReactiveElasticsearchConfig extends ReactiveElasticsearchConfiguration {
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
    }
}

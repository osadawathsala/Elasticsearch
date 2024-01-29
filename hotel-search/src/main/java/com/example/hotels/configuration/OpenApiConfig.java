package com.example.hotels.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

/**
 * @author Osada
 * @Date 1/29/2024
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Spring Boot API")
                        .description("ElasticSearch Spring Boot API")
                        .version("1.0"));
    }
}

package com.biblioteca.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("libro-service", r -> r
                        .path("/libros/**")
                        .uri("http://libro-service:8081"))
                .route("autor-service", r -> r
                        .path("/autores/**")
                        .uri("http://autor-service:8082"))
                .build();
    }
}
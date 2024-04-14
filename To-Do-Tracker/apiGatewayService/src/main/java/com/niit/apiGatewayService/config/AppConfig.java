package com.niit.apiGatewayService.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig
{

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder)
    {

        //routes -- predicate -- filter

        //predicate is a functional interface
        //:: if your interface contains exactly one method then it is a functional interface.

        return builder.routes()
                            .route(p->p
                                .path("/api/v2/**")
                                    .uri("lb://UserAuthenticationService/"))
                            .route(p->p
                                .path("/api/v1/**")
                                    .uri("lb://UserTaskService/"))
                            .build();

    }
}

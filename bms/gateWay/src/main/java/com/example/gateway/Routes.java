package com.example.gateway;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class Routes {

    @Resource
    private DiscoveryClient discoveryClient;

    @Bean
    public RouteLocator userRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("userService", per ->
                        per.path("/userService/**")
                                .uri("http://localhost:9007"))
                .route( per ->
                        per.path("/titleService/**")
                                .uri("http://localhost:9002"))
                .route(per ->
                        per.path("/activitiesService/**")
                                .uri("http://localhost:9003"))
                .route(per ->
                        per.path("/commentService/**")
                                .uri("http://localhost:9005"))
                .route(per ->
                        per.path("/fileService/**")
                                .uri("http://localhost:9006"))
                .build();
    }




//    public String getService(String service) {
//        List<ServiceInstance> instances = discoveryClient.getInstances(service);
//        ServiceInstance serviceInstance = instances.get(0);
//        return serviceInstance.getUri().toString();
//    }
//                                .uri("lb://userService"))


}

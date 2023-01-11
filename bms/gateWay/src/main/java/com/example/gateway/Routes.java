package com.example.gateway;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class Routes {

    @Resource
    private Config config;

    @Resource
    private DiscoveryClient discoveryClient;

    @Bean
    public RouteLocator userRoutes(RouteLocatorBuilder builder) {
        List<String> services = discoveryClient.getServices();
        services.forEach(System.out::println);

        return builder.routes()
                .route(per ->
                        per.predicate(serverWebExchange -> {
                            String parse = Utils.parse(serverWebExchange,"userService");
                            String[] split = parse.split("-");
                            boolean ist = Boolean.parseBoolean(split[0]);
                            if (ist) {
                                config.setUserService(config.getUserService() + split[1]);
                            }
                            return ist;
                        })
                        .uri(config.getUserService()))
                .build();
    }

    @Bean
    public RouteLocator titleRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(per ->
                        per.predicate(serverWebExchange -> {
                            String parse = Utils.parse(serverWebExchange,"titleService");
                            String[] split = parse.split("-");
                            boolean ist = Boolean.parseBoolean(split[0]);
                            if (ist) {
                                config.setTitleService(config.getTitleService() + split[1]);
                            }
                            return ist;
                        })
                                .uri(config.getTitleService()))
                .build();
    }
}

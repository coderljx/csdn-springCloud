package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication(scanBasePackages = "com.example.gateway")
@EnableDiscoveryClient
public class GateWayApplication  {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //1、配置跨域
        corsConfiguration.addAllowedHeader("*");//允许哪些头进行跨域
        corsConfiguration.addAllowedMethod("*");//允许哪些请求方式进行跨域
        corsConfiguration.addAllowedOrigin("*");//允许哪些请求来源进行跨域
        corsConfiguration.setAllowCredentials(true);//是否允许携带cookie进行跨域，否则跨域请求会丢失cookie信息

        source.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsWebFilter(source);
    }

//    @Override  implements Ordered, GlobalFilter
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        return chain.filter(exchange);
//    }
//    /**
//     * 设定过滤器的优先级，值越小则优先级越高
//     * @return
//     */
//    @Override
//    public int getOrder() {
//        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
//    }

}

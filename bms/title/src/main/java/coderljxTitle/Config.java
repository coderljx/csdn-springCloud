package coderljxTitle;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowCredentials(true)
//                .allowedMethods("GET","POST")
//                .allowedHeaders("*");
//    }


    @Bean
    @LoadBalanced  // 打开负载均衡，会根据nacos注册中心 自动换到对应的服务
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

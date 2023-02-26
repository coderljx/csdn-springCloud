package com.codeljxUser;


import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com"})
@MapperScan("com.codeljxUser.Dao")
@ServletComponentScan("com.codeljxUser")
@EnableDiscoveryClient // 注入nacos 注册中心
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
    @Bean
    public IClientConfig iClientConfig() {
        return new DefaultClientConfigImpl();
    }
}

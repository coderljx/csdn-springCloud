package com.codeljxUser;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SpringBootApplication(scanBasePackages = {"com.codeljxUser"})
@MapperScan("com.codeljxUser.Dao")
@EnableDiscoveryClient // 注入nacos 注册中心
@EnableFeignClients("Pojo.openFeign")  // 设置openfeign的扫描路径
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }



    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数
        jedisPoolConfig.setMaxTotal(100);


        JedisPool jPool = new JedisPool(jedisPoolConfig, "172.31.31.129", 6379);
        return jPool;
    }
}

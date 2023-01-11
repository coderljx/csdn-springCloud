package com.example.gateway;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "me")
@Data
public class Config {

    private String userService;
    private String titleService;
}

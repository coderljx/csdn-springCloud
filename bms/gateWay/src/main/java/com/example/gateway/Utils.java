package com.example.gateway;

import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.URI;

public class Utils {

    public static String parse(org.springframework.web.server.ServerWebExchange serverWebExchang,String matchUrl) {
        ServerHttpRequest request = serverWebExchang.getRequest();
        URI uri = request.getURI();
        return uri.getPath().concat(matchUrl) + "-" + uri.getPath();
    }
}

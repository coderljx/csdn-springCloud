package com.hmdp.config;

import Pojo.LjxRedis.RedisString;
import com.alibaba.fastjson2.JSONObject;
import com.hmdp.entity.User;
import com.hmdp.utils.UserHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class Inwork implements HandlerInterceptor, WebMvcConfigurer {

    @Resource()
    private RedisString redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this)
                .excludePathPatterns("/user/login", "/user/code",
                        "/blog/hot", "/shop/**", "/shop-type/**", "/voucher/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("authorization");
        if (header == null || header.length() == 0){
            response.setIntHeader("businss",900);
            return false;
        }

        String entries = redisTemplate.getKey(header);
        if (entries == null || entries.isEmpty()) {
            return false;
        }
        User user = JSONObject.parseObject(entries, User.class);
        UserHolder.saveUser(user);
        redisTemplate.setExpire(header, 30000);
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserHolder.removeUser();
    }


}

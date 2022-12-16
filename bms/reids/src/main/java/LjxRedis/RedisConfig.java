package LjxRedis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate1(RedisConnectionFactory factory){
        // 自己开发一般直接设置成<String,Object>
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 默认设置连接工厂
        template.setConnectionFactory(factory);
        // 序列化配置
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // String 序列化配置
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 所有的key都采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // 所有的hashkey都采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // 所有的value都采用jackson的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // 所有的hashvalue都采用jackson的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);


        template.afterPropertiesSet();
        return template;
    }

}

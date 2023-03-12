package Pojo.LjxRedis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
//        // 自己开发一般直接设置成<String,Object>
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//        configuration.setHostName("172.31.31.129");
//        configuration.setPort(6379);
//        configuration.setDatabase(0);
//
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration);
////        lettuceConnectionFactory.setDatabase(0);
//        // 默认设置连接工厂
//        template.setConnectionFactory(lettuceConnectionFactory);
//
//        // 序列化配置
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        // String 序列化配置
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        // 所有的key都采用String的序列化方式
//        template.setKeySerializer(stringRedisSerializer);
//        // 所有的hashkey都采用String的序列化方式
//        template.setHashKeySerializer(stringRedisSerializer);
//        // 所有的value都采用jackson的序列化方式
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        // 所有的hashvalue都采用jackson的序列化方式
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//
//        template.afterPropertiesSet();
//        return template;
//    }


    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        JedisPool jedisPool = new JedisPool(config,"192.168.0.101",6379);
        return jedisPool;
    }




}

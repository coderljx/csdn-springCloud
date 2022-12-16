package LjxRedis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RedisHash extends LjxRedis.RedisTemplate {

    private HashOperations<String, Object, Object> HashOperations;

    @Autowired
    public RedisHash(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
        HashOperations = super.getRedisTemplate().opsForHash();

//        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
    }


    public void setHash(String key, Map<String,Object> hashMap) {
        HashOperations.putAll(key,hashMap);
    }

    public java.util.Map<Object,Object> getHash(String key) {
       return HashOperations.entries(key);
    }


}

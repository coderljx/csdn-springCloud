package LjxRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisString extends LjxRedis.RedisTemplate {

    private ValueOperations<String, Object> stringObjectValueOperations;

    @Autowired
    public RedisString(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
        stringObjectValueOperations = super.getRedisTemplate().opsForValue();

//        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
    }

    /**
     * 写入一个string
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        stringObjectValueOperations.set(key,value);
    }


    public void setString(String key, String value,long time) {
        stringObjectValueOperations.set(key,value, Duration.ofMinutes(time));
    }

    public Object getKey(String key) {
        return stringObjectValueOperations.get(key);
    }



}

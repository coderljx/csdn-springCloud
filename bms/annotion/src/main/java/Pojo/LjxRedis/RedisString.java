package Pojo.LjxRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisString extends Pojo.LjxRedis.RedisTemplate {

    private final Jedis jedis;

    @Autowired
    public RedisString(JedisPool jedisPool) {
        super(jedisPool);
        this.jedis = super.getJedis();
    }

    /**
     * 写入一个string
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        jedis.set(key,value);
    }


    /**
     * 设置key的过期时间
     * @param key
     * @param value
     * @param time
     */
    public void setString(String key, String value,int time) {
        jedis.set(key,value);
        setExpire(key,time);
    }

    /**
     * 获取一个key
     * @param key
     * @return
     */
    public Object getKey(String key) {
        return jedis.get(key);
    }

    /**
     *
     * @param key 需要设置的key
     * @param time 毫秒格式 1000 = 1s
     */
    public void setExpire(String key,int time){
        jedis.expire(key,time);
    }


}

package Pojo.LjxRedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

class RedisTemplate {

    private JedisPool jedisPool;

    public RedisTemplate(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    Jedis getJedis() {
        return jedisPool.getResource();
    }

}

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

    void showJedis(Jedis jedis) {
        // 释放链接，不然会报错
        if (jedis != null) {
            jedis.close();
        }
    }

}

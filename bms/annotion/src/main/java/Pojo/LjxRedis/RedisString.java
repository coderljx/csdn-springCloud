package Pojo.LjxRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisString extends Pojo.LjxRedis.RedisTemplate {


    /**
     * 从来连接池拿一个jedis出来，执行完毕再释放 不然消耗完毕之后 再访问redis会报错
     * @param jedisPool
     */
    @Autowired
    public RedisString(JedisPool jedisPool) {
        super(jedisPool);
    }

    /**
     * 写入一个string
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        Jedis jedis = super.getJedis();
        jedis.set(key,value);
        super.showJedis(jedis);
    }


    /**
     * 设置key的过期时间
     * @param key
     * @param value
     * @param time
     */
    public void setString(String key, String value,int time) {
        Jedis jedis = super.getJedis();
        jedis.set(key,value);
        setExpire(key,time);
        super.showJedis(jedis);
    }

    /**
     * 获取一个key
     * @param key
     * @return
     */
    public String getKey(String key) {
        Jedis jedis = super.getJedis();
        String data = jedis.get(key);
        super.showJedis(jedis);
        return data;
    }

    /**
     *
     * @param key 需要设置的key
     * @param time 毫秒格式 1000 = 1s
     */
    public void setExpire(String key,int time){
        Jedis jedis = super.getJedis();
        jedis.expire(key,time);
        super.showJedis(jedis);
    }





}

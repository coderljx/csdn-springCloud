package Pojo.LjxRedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisString extends Pojo.LjxRedis.RedisTemplate {


    /**
     * 从来连接池拿一个jedis出来，执行完毕再释放 不然消耗完毕之后 再访问redis会报错
     *
     * @param jedisPool
     */
    @Autowired
    public RedisString(JedisPool jedisPool) {
        super(jedisPool);
    }

    /**
     * 写入一个string
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        Jedis jedis = this.getJedis();
        jedis.set(key, value);
        super.showJedis(jedis);
    }


    /**
     * 设置key的过期时间
     *
     * @param key
     * @param value
     * @param time
     */
    public void setString(String key, String value, int time) {
        Jedis jedis = this.getJedis();
        jedis.set(key, value);
        setExpire(key, time);
        super.showJedis(jedis);
    }

    /**
     * 获取一个key
     *
     * @param key
     * @return
     */
    public String getKey(String key) {
        Jedis jedis = this.getJedis();
        String data = jedis.get(key);
        super.showJedis(jedis);
        return data;
    }

    /**
     * @param key  需要设置的key
     * @param time 毫秒格式 1000 = 1s
     */
    public void setExpire(String key, int time) {
        Jedis jedis = this.getJedis();
        jedis.expire(key, time);
        super.showJedis(jedis);
    }

    /**
     * 删除key
     *
     * @param key
     */
    public void del(String... key) {
        if (key.length == 0) return;
        Jedis jedis = this.getJedis();
        jedis.del(key);
        super.showJedis(jedis);
    }


    public Jedis getJedis() {
        return super.getJedis();
    }

    /**
     * 将一个key 自增长,如果该key不存在 那么会直接新增该key 并且赋值1
     * @param key
     * @return
     */
    public long incr(String key) {
        Jedis jedis = this.getJedis();
        return jedis.incr(key);
    }

    /**
     * 自定义该key 每次增长多少
     * @param key
     * @param incrValue
     * @return
     */
    public long incrBy(String key,long incrValue) {
        Jedis jedis = this.getJedis();
        return jedis.incrBy(key, incrValue);
    }


}

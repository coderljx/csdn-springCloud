import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ads {

    private static JedisPool jedisPool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        jedisPool = new JedisPool(config, "192.168.0.100", 6379);
    }

    public static void main(String[] args) {
        Jedis resource = jedisPool.getResource();

        resource.close();

    }



}

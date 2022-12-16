package LjxRedis;

class RedisTemplate {

    private final org.springframework.data.redis.core.RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate(org.springframework.data.redis.core.RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public org.springframework.data.redis.core.RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    public Boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


}

package com.hmdp.service.impl;

import Pojo.LjxRedis.RedisString;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.hmdp.utils.RedisConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Resource()
    private RedisString redisTemplate;


    @Override
    public Result queryById(Long id) {
        String shopKey = RedisConstants.CACHE_SHOP_KEY + id;
        String key = redisTemplate.getKey(shopKey);

        if (key == null || key.isEmpty()) {
            Shop mysqlData = super.getById(id);
            // 如果redis没有 mysql也没有 为了防止缓存穿透 讲null数据写入到redis中
            if (mysqlData == null || (mysqlData.getId() == null || mysqlData.getId() <= 0L)) {
                redisTemplate.setString(shopKey, "{}", (int) (long) RedisConstants.LOCK_SHOP_TTL);
            }else {
                key = JSONObject.toJSONString(mysqlData);
                redisTemplate.setString(shopKey, key, (int) (long) RedisConstants.LOCK_SHOP_TTL);
            }
            return Result.ok(mysqlData);
        }

        return Result.ok(JSONObject.parse(key));
    }

    @Override
    public Result updates(Shop shop) {
        if (shop.getId() == null) {
            return Result.fail("id不能为空");
        }
        super.updateById(shop);
        redisTemplate.del(RedisConstants.CACHE_SHOP_KEY + shop.getId());
        return Result.ok();
    }




}

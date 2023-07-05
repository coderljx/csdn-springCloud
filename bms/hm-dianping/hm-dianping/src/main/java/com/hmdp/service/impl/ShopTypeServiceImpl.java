package com.hmdp.service.impl;

import Pojo.LjxRedis.RedisString;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.hmdp.utils.RedisConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {
    @Resource()
    private RedisString redisTemplate;

    @Override
    public Result queryShopType() {
        String shopType = RedisConstants.SHOP_TYPE;

        /**
         * 不能使用list 因为list使用一次后 数据就被拿出来了 之后则读取不到该数据了
         */
        String key = redisTemplate.getKey(shopType);
        if (key == null || key.isEmpty()) {
            List<ShopType> typeList = super.query().orderByAsc("sort").list();
            redisTemplate.setString(shopType,JSONArray.toJSONString(typeList));
            return Result.ok(typeList);
        }
        return Result.ok(JSONArray.parse(key));
    }
}

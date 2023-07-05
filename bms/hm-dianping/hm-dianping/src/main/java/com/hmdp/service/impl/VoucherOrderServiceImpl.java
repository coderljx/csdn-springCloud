package com.hmdp.service.impl;

import Pojo.LjxRedis.RedisString;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.entity.SeckillVoucher;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.mapper.VoucherOrderMapper;
import com.hmdp.service.ISeckillVoucherService;
import com.hmdp.service.IVoucherOrderService;
import com.hmdp.utils.UserHolder;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {

    @Autowired
    private RedisString redisString;
    @Autowired
    private ISeckillVoucherService iSeckillVoucherService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public  Result seckill(Long voucherId) {
        SeckillVoucher byId = iSeckillVoucherService.getById(voucherId);
        if (byId == null || byId.getBeginTime() == null || byId.getEndTime() == null) {
            return Result.fail("请选择正确的券");
        }
        if (LocalDateTime.now().isBefore(byId.getBeginTime())) {
            return Result.fail("活动还未开始");
        }
        if (LocalDateTime.now().isAfter(byId.getEndTime())) {
            return Result.fail("活动已经结束啦");
        }
        if (byId.getStock() < 1) {
            return Result.fail("库存不足啦");
        }
        boolean isOk = iSeckillVoucherService.update()
                .setSql("stock = stock - 1")
                .eq("voucher_id", voucherId)
                .gt("stock",0)
                .update();
        Object o = AopContext.currentProxy();
        if (!isOk) {
            return Result.fail("库存不足啦");
        }
        VoucherOrder voucherOrder = new VoucherOrder();
        long order = redisString.incr("order");
        Long userId = UserHolder.getUser().getId();
        voucherOrder.setId(order);
        voucherOrder.setUserId(userId);
        voucherOrder.setVoucherId(voucherId);
        save(voucherOrder);

        return Result.ok(order);
    }


}

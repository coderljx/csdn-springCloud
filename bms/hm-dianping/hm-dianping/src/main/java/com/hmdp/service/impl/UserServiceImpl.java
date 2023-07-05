package com.hmdp.service.impl;

import Pojo.LjxRedis.RedisString;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.RegexUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource()
    private RedisString redisTemplate;

    @Override
    public Result sendCode(String phone, HttpSession session) {
        if (RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手机号格式不正确");
        }
        String code = RandomUtil.randomNumbers(4);
        redisTemplate.setString(RedisConstants.LOGIN_CODE_KEY + phone, code,
                (int) (long) RedisConstants.LOGIN_CODE_TTL);
        System.out.println(code);
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        if (RegexUtils.isPhoneInvalid(loginForm.getPhone())) {
            return Result.fail("手机号格式不正确");
        }

        String code = redisTemplate.getKey(RedisConstants.LOGIN_CODE_KEY + loginForm.getPhone());
        if (code == null || code.length() == 0) {
            return Result.fail("session为空");
        }
        if (!code.equals(loginForm.getCode())) {
            return Result.fail("code错误");
        }
        User user = super.query().eq("phone", loginForm.getPhone()).one();
        if (user == null) {
            user = new User();
            user.setPhone(loginForm.getPhone());
            String name = "wxid_" + RandomUtil.randomString(10);
            user.setNickName(name);
            save(user);
        }
        String id = UUID.randomUUID().toString();
        redisTemplate.setString(id, JSONObject.toJSONString(user),30000);

        return Result.ok(id);
    }


}

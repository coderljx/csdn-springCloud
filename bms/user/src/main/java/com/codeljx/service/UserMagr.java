package com.codeljx.service;

import DB.User;
import LjxRedis.RedisHash;
import LjxRedis.RedisString;
import LjxUtils.UUID;
import com.codeljx.Dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class UserMagr {

    @Resource
    private RedisString string;
    @Resource
    private RedisHash hash;
    @Resource
    private UserDao userDao;


    public void test() {
        hash.hasKey("");
        Map<Object, Object> tt = hash.getHash("tt");
        String uuid = UUID.getUUID();

        System.out.println(tt.size());
    }


    public void registUser(User user) {
        userDao.insertUser(user);
    }


}

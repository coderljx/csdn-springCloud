package com.codeljx.service;

import DB.Log;
import DB.User;
import LjxEx.TypeException;
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

    /**
     * 用户登录
     * @param user
     * @throws TypeException
     */
    public void loginUser(User user) throws TypeException{
        User user1 = userDao.queryUserFormPwd(user);
        if (user1 == null || user1.getName().equals("")) throw new TypeException("用户密码错误");

        string.setString(user1.getId() + "",UUID.getUUID());
    }

    public void updateUser(User user) {
        userDao.updateUser(user,"ljx");
    }


}

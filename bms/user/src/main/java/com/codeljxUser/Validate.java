package com.codeljxUser;


import LjxRedis.RedisString;
import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.StringUtils;
import com.codeljxUser.Dao.UserDao;

import javax.annotation.Resource;

public class Validate {

    @Resource
    private UserDao userDao;
    @Resource
    private RedisString redisString;

    public Boolean validate(String appid, String appkey, String sign,  String params) throws TypeException {

        return true;
    }

    public User validate(String appid, Integer userid,String sign, String params)  {

//        this.validate(appid, userid,sign);

        return this.validate(appid, userid,sign);
    }

    public User validate(String appid, Integer userid,String sign) {

        String key = (String) redisString.getKey(userid + "");
        if (StringUtils.isEmp(key)) throw new TypeException("用户未登录或已过期");
        return userDao.querUserById(userid);
    }
}

package com.codeljxUser;


import LjxRedis.RedisString;
import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.StringUtils;
import Pojo.SearchArgsMap;
import com.codeljxUser.Dao.UserDao;

import javax.annotation.Resource;

public class Validate {

    @Resource
    private UserDao userDao;
    @Resource
    private RedisString redisString;

    public Boolean validate(String appid, String appkey, String sign,  String data) throws NoSuchFieldException, IllegalAccessException {

        return true;
    }

    public User validate(String appid, Integer userid,String sign, String data) throws NoSuchFieldException, IllegalAccessException {

//        this.validate(appid, userid,sign);

        return this.validate(appid, userid,sign);
    }

    public User validate(String appid, Integer userid,String data) throws NoSuchFieldException, IllegalAccessException{

        String key = (String) redisString.getKey(userid + "");
        if (StringUtils.isEmp(key)) throw new TypeException("E000001_01");
        User user = userDao.querUserById(userid);
        SearchArgsMap searchArgsMap = parseData(data);
        user.setSearchArgsMap(searchArgsMap);
        return user;
    }

    public SearchArgsMap validate(String appid, String data) throws NoSuchFieldException, IllegalAccessException {
        return parseData(data);
    }


    public void validate(String appid, String sign,String data)  {

    }


    private SearchArgsMap parseData(String data) throws NoSuchFieldException, IllegalAccessException {
        return new SearchArgsMap(data);
    }


    public void validate(String appid) throws NoSuchFieldException, IllegalAccessException {
    }


}

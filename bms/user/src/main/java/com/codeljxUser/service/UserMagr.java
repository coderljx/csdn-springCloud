package com.codeljxUser.service;


import Pojo.DB.Follow;
import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxRedis.RedisString;
import Pojo.LjxUtils.ResponseParse;
import Pojo.LjxUtils.StringUtils;
import Pojo.LjxUtils.UUID;
import Pojo.SearchArgs;
import com.alibaba.fastjson2.JSONObject;
import com.codeljxUser.Dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserMagr {

    @Resource
    private RedisString string;
    @Resource
    private UserDao userDao;
    @Resource
    private Pojo.openFeign.FileOpenFeign fileOpenFeign;

    public User registUser(User user) {
        userDao.insertUser(user);

        User resUser = new User(user.getId());
        return resUser;
    }

    /**
     * 用户登录
     *
     * @param user
     * @throws TypeException
     */
    public User loginUser(User user) {
        User searchUser;
        if (StringUtils.isEmp(user.getPhone()) || StringUtils.isEmp(user.getPassword()))
            throw new TypeException("E000001_11");
        // 状态为1 使用账号密码登录
        searchUser = userDao.queryUserFormPhone(user);
        if (searchUser == null || StringUtils.isEmp(searchUser.getName())) {
            throw new TypeException("E000001_06");
        }

        // 用户的账号密码验证成功后，会直接将用户的数据写入到redis中,只有redis中有数据的用户 才算是登录有效的
        String uuid = UUID.getUUID();
        searchUser.setUserLoginKey(uuid);
        string.setString(searchUser.getId() + "", JSONObject.toJSONString(searchUser));

        String userAvactor = fileOpenFeign.getUserAvactor(user.getAppId(), searchUser.getId());
        JSONObject jsonObject = ResponseParse.getJsonObject(userAvactor);
        if (jsonObject != null && jsonObject.containsKey("data")) {
            searchUser.setAvatar((String) jsonObject.get("data"));
        }
        return searchUser;
    }


    public void updateUser(User user) {
        userDao.updateUser(user, "ljx");
    }

    /**
     * 设置用户权限
     *
     * @param roleid
     */
    public void setUserRole(Integer roleid, Integer userid) {
        User user = userDao.querUserById(userid);
        user.setRoleid(roleid);
        userDao.updateUser(user, user.getName());
    }


    public List<User> getUser() {
        List<User> users = userDao.queryUser();
        return users;
    }

    /**
     * 删除用户
     *
     * @param userid 用户id
     */
    public void deleteUser(Integer delUserid, Integer userid) {
        User user = userDao.querUserById(userid);
        userDao.deleteUser(delUserid, user.getName());
    }


    /**
     * 关注用户接口，只支持单个操作
     *
     * @param user
     */
    public void followUser(User user) {
        List<SearchArgs.Condition> children = user.getSearchArgsMap().getArgsItem().getChildren();
        SearchArgs.Condition condition = children.get(0);
        //如果这个用户是自己关注自己，那么不允许
        if (user.getId().equals(Integer.parseInt(condition.getValue()))) {
            throw new TypeException("E000001_12");
        }
        User user1 = userDao.querUserById(user.getId());
        /**
         * 如果当前用户没有关注过该用户，才能进行关注，不能重复关注
         */
        Follow follow = userDao.queryFollow(user.getId(), Integer.parseInt(condition.getValue()));
        if (follow == null || follow.getId() <= 0) {
            userDao.followUser(user.getId(), Integer.parseInt(condition.getValue()), user1.getName());
        }
    }


    /**
     * 取关用户,支持批量取关
     *
     * @param user
     */
    public void unFollowUser(User user) {
        List<SearchArgs.Condition> children = user.getSearchArgsMap().getArgsItem().getChildren();
        List<String> followList = new ArrayList<>();
        for (SearchArgs.Condition child : children) {
            followList.add(child.getValue());
        }
        userDao.unFollowUser(user.getId(), followList);
    }


    /**
     * 获取用户登录状态，
     * 查询redis中 该用户是否登录
     *
     * @param userid
     * @return
     */
    public User getUserLoginStatus(Integer userid) {
        if (userid == null || userid <= 0) {
            throw new TypeException("E000001_02");
        }
//        User user = userDao.querUserById(userid);
//        user.setUserLoginKey(string.getKey(userid + ""));
        String key = string.getKey(userid + "");
        User user = JSONObject.parseObject(key, User.class);
        return user;
    }


    /**
     * 获取当前用户的关注人列表
     *
     * @param userid
     */
    public List<Follow> getUserFollow(Integer userid) {
        return userDao.getUserFollow(userid);
    }


}

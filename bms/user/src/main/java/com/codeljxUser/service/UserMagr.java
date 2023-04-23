package com.codeljxUser.service;


import Pojo.DB.Follow;
import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxRedis.RedisString;
import Pojo.LjxUtils.UUID;
import Pojo.SearchArgs;
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

    public void registUser(User user) {
        userDao.insertUser(user);
    }

    /**
     * 用户登录
     * @param user
     * @throws TypeException
     */
    public User loginUser(User user,int status){
        User user1 = null;
        if (status == 1) {
            user1 = userDao.queryUserFormPhone(user);
            if (user1 == null || user1.getName().equals("")) throw new TypeException("E000001_04");
        }
        if (status == 3) {
            user1 = userDao.queryUserFormPwd(user);
            if (user1 == null || user1.getName().equals("")) throw new TypeException("E000001_05");
        }
        String uuid = UUID.getUUID();
        string.setString(user1.getId() + "",uuid);
        return user1;
    }


    public void updateUser(User user) {
        userDao.updateUser(user,"ljx");
    }

    /**
     * 设置用户权限
     * @param roleid
     */
    public void setUserRole(Integer roleid,Integer userid){
        User user = userDao.querUserById(userid);
        user.setRoleid(roleid);
        userDao.updateUser(user,user.getName());
    }
    
    
    public List<User> getUser() {
        List<User> users = userDao.queryUser();
        return users;
    }

    /**
     * 删除用户
     * @param userid 用户id
     */
    public void deleteUser(Integer delUserid,Integer userid){
        User user = userDao.querUserById(userid);
        userDao.deleteUser(delUserid,user.getName());
    }


    /**
     * 关注用户接口，只支持单个操作
     * @param user
     */
    public void followUser(User user) {
        List<SearchArgs.Condition> children = user.getSearchArgsMap().getArgsItem().getChildren();
        SearchArgs.Condition condition = children.get(0);
        User user1 = userDao.querUserById(user.getId());

        /**
         * 如果当前用户没有关注过该用户，才能进行关注，不能重复关注
         */
        Follow follow = userDao.queryFollow(user.getId(), Integer.parseInt(condition.getValue()));
        if (follow == null || follow.getId() <= 0) {
            userDao.followUser(user.getId(), Integer.parseInt(condition.getValue()),user1.getName());
        }
    }


    /**
     * 取关用户,支持批量取关
     * @param user
     */
    public void unFollowUser(User user) {
        List<SearchArgs.Condition> children = user.getSearchArgsMap().getArgsItem().getChildren();
        List<String> followList = new ArrayList<>();
        for (SearchArgs.Condition child : children) {
            followList.add(child.getValue());
        }
        userDao.unFollowUser(user.getId(),followList);
    }


    /**
     * 获取用户登录状态，
     * 查询redis中 该用户是否登录
     * @param userid
     * @return
     */
    public User getUserLoginStatus(Integer userid) {
        if (userid == null || userid <= 0) {
            throw new TypeException("E000001_02");
        }
        User user = userDao.querUserById(userid);
        user.setUserLoginKey(string.getKey(userid + ""));
        return user;
    }


    /**
     * 获取当前用户的关注人列表
     * @param userid
     */
    public List<Follow> getUserFollow(Integer userid) {
        List<Follow> userFollow = userDao.getUserFollow(userid);
        return userFollow;
    }






}

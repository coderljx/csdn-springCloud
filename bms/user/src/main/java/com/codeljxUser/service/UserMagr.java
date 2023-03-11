package com.codeljxUser.service;


import LjxRedis.RedisString;
import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
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
    public void setUserRole(Integer roleid,String modify){
        User user = new User();
        user.setRoleid(roleid);
        userDao.updateUser(user,modify);
    }
    
    
    public List<User> getUser() {
        List<User> users = userDao.queryUser();
        return users;
    }

    /**
     * 删除用户
     * @param userid 用户id
     * @param modify 操作人
     */
    public void deleteUser(Integer userid,String modify){
        userDao.deleteUser(userid,modify);
    }


    /**
     * 关注用户接口，只支持单个操作
     * @param user
     */
    public void followUser(User user) {
        List<SearchArgs.Condition> children = user.getSearchArgsMap().getArgsItem().getChildren();
        SearchArgs.Condition condition = children.get(0);
        userDao.followUser(user.getId(), Integer.parseInt(condition.getValue()),user.getName());
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


}

package com.codeljxUser.service;


import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.UUID;
import LjxRedis.RedisHash;
import LjxRedis.RedisString;
import com.codeljxUser.Dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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


}

package com.codeljxUser.Api;


import Pojo.Consumer.Consumet;
import Pojo.DB.Follow;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.StringUtils;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import com.alibaba.fastjson2.JSONObject;
import com.codeljxUser.service.UserMagr;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/user")
public class UserService extends Validate {

    @Resource
    private UserMagr userMagr;

    @PostMapping("/login/{appid}")
    @LogEs(url = "/user/login", dec = "用户登录")
    public Response<?> userLogin(
            @PathVariable String appid,
            @RequestBody String data
    ) {
        return Consumet.Logic(() -> {
            AtomicReference<User> user = new AtomicReference<>();
            validate(appid);
            user.set(JSONObject.parseObject(data, User.class));
            user.set(userMagr.loginUser(user.get()));
            return user.get();
        });
    }


    @PostMapping("/register/{appid}")
    @LogEs(url = "/user/register", dec = "注册用户")
    public Response<?> registUser(
            @PathVariable String appid,
            @RequestParam(value = "", required = false) String appkey,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        return Consumet.Logic(() -> {
            AtomicReference<User> user = new AtomicReference<>();
            validate(appid, appkey, sign, data);
            user.set(JSONObject.parseObject(data, User.class));

            if (StringUtils.isEmp(user.get().getName())) {
                throw new DataException("用户名不能为空");
            }
            if (StringUtils.isEmp(user.get().getPassword())) {
                throw new DataException("密码不能为空");
            }
            userMagr.registUser(user.get());
            return null;
        });
    }


    @PostMapping("/update/{appid}/{userid}")
    @LogEs(url = "/user/update", dec = "修改用户信息")
    public Response<?> updateUser(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        return Consumet.Logic(() -> {
            User validate = validate(appid, userid, sign, data);
            User user = JSONObject.parseObject(data, User.class);

            if (StringUtils.isEmp(user.getName())) {
                throw new TypeException("E000001_06");
            }
            return null;
        });
    }


    @PostMapping("/role/{appid}/{userid}")
    @LogEs(url = "/user/role", dec = "设置用户权限")
    public Response<?> roleUser(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) Integer roleid,
            @RequestParam(value = "", required = false) String sign
    ) {
        return Consumet.Logic(() -> {
            User validate = validate(appid, userid, sign);

            if (StringUtils.isEmp(userid + "")) {
                throw new TypeException("E000001_02");
            }
            if (StringUtils.isEmp(roleid + "")) {
                throw new TypeException("E000001_03");
            }

            userMagr.setUserRole(roleid, userid);
            return null;
        });
    }


    @GetMapping("/gets/{appid}")
    @LogEs(url = "/user/gets", dec = "获取用户")
    public Response<?> getUser(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign
    ) {
        return Consumet.Logic(() -> userMagr.getUser());
    }


    @PostMapping("/delete/{appid}/{userid}")
    @LogEs(url = "/user/delete", dec = "用户注销")
    public Response<?> deleteUser(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestParam(value = "", required = false) Integer delUserid
    ) {
        return Consumet.Logic(() -> {
            User validate = validate(appid, userid, sign);
            userMagr.deleteUser(delUserid, userid);
            return null;
        });
    }


    @PostMapping("/follow/{appid}")
    @LogEs(url = "/user/follow", dec = "关注用户")
    public Response<?> followUser(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        return Consumet.Logic(() -> {
            User validate = validate(appid, userid, data);

            userMagr.followUser(validate);
            return null;
        });
    }


    @PostMapping("/unfollow/{appid}")
    @LogEs(url = "/user/unfollow", dec = "取关用户")
    public Response<?> unFollowUser(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        return Consumet.Logic(() -> {
            User validate = validate(appid, userid, data);

            userMagr.unFollowUser(validate);
            return null;
        });
    }


    @GetMapping("/getUserStatus/{appid}")
    @LogEs(url = "/user/getUserStatus", dec = "获取用户登录状态")
    public Response<?> getUserLoginStatus(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign
    ) {
        return Consumet.Logic(() -> userMagr.getUserLoginStatus(userid));
    }


    @GetMapping("/getUserFollow/{appid}")
    @LogEs(url = "/user/getUserFollow", dec = "获取用户关注人列表")
    public Response<?> getUserFollow(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "sign", required = false) String sign
    ) {

        return Consumet.Logic(() -> {
            List<Follow> userFollow;
            if (userid == null || userid <= 0) {
                throw new DataException("userid");
            }
            userFollow = userMagr.getUserFollow(userid);
            return userFollow;
        });
    }


}

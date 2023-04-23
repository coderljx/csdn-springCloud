package com.codeljxUser.Api;


import Pojo.DB.Coco;
import Pojo.DB.Follow;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.StringUtils;
import Pojo.LjxUtils.UUID;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import com.alibaba.fastjson2.JSONObject;
import com.codeljxUser.service.UserMagr;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserService extends Validate {

    @Resource
    private UserMagr userMagr;

    @PostMapping("/login/{appid}")
    @LogEs(url = "/user/login", dec = "用户登录")
    public Response<?> userLogin(
            @PathVariable String appid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        User user = null;
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
//            validate(appid,userid,data);
            user = JSONObject.parseObject(data, User.class);
            Map map = JSONObject.parseObject(data, Map.class);
            int status = (int) map.get("status");

            switch (status) {
//                case 1 : break;
//                case 2 : break;
                case 3:
                    if (StringUtils.isEmp(user.getName())) {
                        throw new DataException("用户名不能为空");
                    }
                    if (StringUtils.isEmp(user.getPassword())) {
                        throw new DataException("密码不能为空");
                    }
                    break;

                default:
                    break;
            }
            user = userMagr.loginUser(user, status);
            coco.code = 200;
            coco.message = "Success";
        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
            e.printStackTrace();
        } finally {
            response = new Response<>(coco, user);
        }
        return response;
    }


    @PostMapping("/register/{appid}")
    @LogEs(url = "/user/register", dec = "注册用户")
    public Response<?> registUser(
            @PathVariable String appid,
            @RequestParam(value = "", required = false) String appkey,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        User user = null;
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            validate(appid, appkey, sign, data);
            user = JSONObject.parseObject(data, User.class);

            if (StringUtils.isEmp(user.getName())) {
                throw new DataException("用户名不能为空");
            }
            if (StringUtils.isEmp(user.getPassword())) {
                throw new DataException("密码不能为空");
            }
            userMagr.registUser(user);
            coco.code = 200;
            coco.message = "Success";
        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
        } finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @PostMapping("/update/{appid}/{userid}")
    @LogEs(url = "/user/update", dec = "修改用户信息")
    public Response<?> updateUser(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        User user = null;
        Coco coco = null;
        Response<?> response = null;
        try {
            User validate = validate(appid, userid, sign, data);
            user = JSONObject.parseObject(data, User.class);

            if (StringUtils.isEmp(user.getName())) {
                throw new TypeException("E000001_06");
            }
            coco = Coco.ok;
        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco = Coco.InitCoco;
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco = Coco.InitCoco;
            coco.code = -101;
            coco.message = e.getMessage();
        } finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @PostMapping("/role/{appid}/{userid}")
    @LogEs(url = "/user/role", dec = "设置用户权限")
    public Response<?> roleUser(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) Integer roleid,
            @RequestParam(value = "", required = false) String sign
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User validate = validate(appid, userid, sign);

            if (StringUtils.isEmp(userid + "")) {
                throw new TypeException("E000001_02");
            }
            if (StringUtils.isEmp(roleid + "")) {
                throw new TypeException("E000001_03");
            }

            userMagr.setUserRole(roleid, userid);
            coco.code = 200;
            coco.message = "Success";
        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
        } finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @GetMapping("/gets/{appid}")
    @LogEs(url = "/user/gets", dec = "获取用户")
    public Response<?> getUser(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign
    ) {
        Coco coco = Coco.InitCoco;
        List<User> user = null;
        Response<?> response = null;
        try {
            user = userMagr.getUser();
            coco.code = 200;
            coco.message = "Success";
        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
        } finally {
            response = new Response<>(coco, user);
        }
        return response;
    }


    @PostMapping("/delete/{appid}/{userid}")
    @LogEs(url = "/user/delete", dec = "用户注销")
    public Response<?> deleteUser(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestParam(value = "", required = false) Integer delUserid
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User validate = validate(appid, userid, sign);
            userMagr.deleteUser(delUserid, userid);

            coco.code = 200;
            coco.message = "Success";
        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
        } finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @PostMapping("/follow/{appid}")
    @LogEs(url = "/user/follow", dec = "关注用户")
    public Response<?> followUser(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User validate = validate(appid, userid, data);

            userMagr.followUser(validate);

            coco.code = 200;
            coco.message = "Success";
        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
        } finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @PostMapping("/unfollow/{appid}")
    @LogEs(url = "/user/unfollow", dec = "取关用户")
    public Response<?> unFollowUser(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User validate = validate(appid, userid, data);

            userMagr.unFollowUser(validate);

            coco.code = 200;
            coco.message = "Success";
        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
        } finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @GetMapping("/getUserStatus/{appid}")
    @LogEs(url = "/user/getUserStatus", dec = "获取用户登录状态")
    public Response<?> getUserLoginStatus(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        User res = new User();
        try {
            res = userMagr.getUserLoginStatus(userid);
            coco.code = 200;
            coco.message = "Success";
        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
        } finally {
            response = new Response<>(coco, res);
        }
        return response;
    }





    @GetMapping("/getUserFollow/{appid}")
    @LogEs(url = "/user/getUserFollow", dec = "获取用户关注人列表")
    public Response<?> getUserFollow(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "sign", required = false) String sign
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        List<Follow> userFollow = null;
        try {
            if (userid == null || userid <= 0) {
                throw new DataException("userid");
            }
            userFollow = userMagr.getUserFollow(userid);

            coco.code = 200;
            coco.message = "Success";
        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
        } finally {
            response = new Response<>(coco,userFollow);
        }
        return response;
    }



}

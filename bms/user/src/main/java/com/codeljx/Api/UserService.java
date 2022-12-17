package com.codeljx.Api;


import DB.Coco;
import DB.Response;
import DB.User;
import LjxEx.TypeException;
import LjxUtils.StringUtils;
import LjxUtils.Validate;
import an.Log.LogEs;
import com.alibaba.fastjson2.JSONObject;
import com.codeljx.service.UserMagr;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserService extends Validate {

    @Resource
    private UserMagr userMagr;


    @PostMapping("/login")
    @LogEs(url = "/user/login",dec = "用户登录")
    public Response<?> userLogin(@RequestParam(value = "",required = false) String appid,
                                  @RequestParam(value = "",required = false) String appkey,
                                  @RequestBody String data
    ){
        User user = null;
        Coco coco = Coco.ok;
        try {
            validate(appid, appkey, data);
            user = JSONObject.parseObject(data, User.class);
            if (StringUtils.isEmp(user.getName())){
                throw new TypeException("用户名不能为空");
            }
            if (StringUtils.isEmp(user.getPassword())){
                throw new TypeException("密码不能为空");
            }
            userMagr.loginUser(user);
        }catch (TypeException message) {
            coco.code = -100;
            coco.message = message.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
        }
        return new Response<>(coco);
    }


    @PostMapping("/register")
    @LogEs(url = "/user/register",dec = "注册用户")
    public Response<?> registUser(@RequestParam(value = "",required = false) String appid,
                                  @RequestParam(value = "",required = false) String appkey,
                                  @RequestBody String data
    ){
        User user = null;
        Coco coco = Coco.ok;
        try {
            validate(appid, appkey, data);
            user = JSONObject.parseObject(data, User.class);

            if (StringUtils.isEmp(user.getName())){
                throw new TypeException("用户名不能为空");
            }
            if (StringUtils.isEmp(user.getPassword())){
                throw new TypeException("密码不能为空");
            }
            userMagr.registUser(user);
        }catch (TypeException message) {
            coco.code = -100;
            coco.message = message.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
        }
        return new Response<>(coco);
    }


    @PostMapping("/update")
    @LogEs(url = "/user/update",dec = "修改用户信息")
    public Response<?> registUser(@RequestParam(value = "",required = false) String appid,
                                  @RequestParam(value = "",required = false) String appkey,
                                  @RequestParam(value = "",required = false) Integer userid,
                                  @RequestBody String data
    ){
        User user = null;
        Coco coco = Coco.ok;
        try {
            validate(appid, appkey, userid, data);
            user = JSONObject.parseObject(data, User.class);

            if (StringUtils.isEmp(user.getName())){
                throw new TypeException("用户名不能为空");
            }

        }catch (TypeException message) {
            coco.code = -100;
            coco.message = message.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
        }
        return new Response<>(coco);
    }






    @GetMapping("/")
    public String a () {

        userMagr.test();
        return  "ok";
    }





}

package com.codeljxUser.Api;



import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.StringUtils;
import an.Log.LogEs;
import com.alibaba.fastjson2.JSONObject;
import com.codeljxUser.Validate;
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


    @PostMapping("/login/{appid}/{userid}")
    @LogEs(url = "/user/login",dec = "用户登录")
    public Response<?> userLogin(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestBody String data
    ){
        User user = null;
        Coco coco = Coco.ok;
        Response<?> response = null;
        try {
//            validate(appid,userid,data);
            user = JSONObject.parseObject(data, User.class);
            Map map = JSONObject.parseObject(data, Map.class);
            int status = (int) map.get("status");

            switch (status) {
                case 1 : break;
                case 2 : break;
                case 3 :
                    if (StringUtils.isEmp(user.getName())){
                        throw new TypeException("用户名不能为空");
                    }
                    if (StringUtils.isEmp(user.getPassword())){
                        throw new TypeException("密码不能为空");
                    }
                    break;
            }
            user = userMagr.loginUser(user, status);
        }catch (TypeException message) {
            coco.code = -100;
            coco.message = message.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
        }finally {
            response = new Response<>(coco,user);
        }
        return response;
    }


    @PostMapping("/register/{appid}")
    @LogEs(url = "/user/register",dec = "注册用户")
    public Response<?> registUser(
            @PathVariable String appid,
            @RequestParam(value = "",required = false) String appkey,
            @RequestParam(value = "",required = false) String sign,
            @RequestBody String data
    ){
        User user = null;
        Coco coco = Coco.ok;
        Response<?> response = null;
        try {
            validate(appid, appkey,sign, data);
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
        }finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @PostMapping("/update/{appid}/{userid}")
    @LogEs(url = "/user/update",dec = "修改用户信息")
    public Response<?> updateUser(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestBody String data
    ){
        User user = null;
        Coco coco = Coco.ok;
        Response<?> response = null;
        try {
            User validate = validate(appid, userid, sign,data);
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
        }finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @PostMapping("/role/{appid}/{userid}")
    @LogEs(url = "/user/role",dec = "设置用户权限")
    public Response<?> roleUser(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) Integer roleid,
            @RequestParam(value = "",required = false) String sign
    ){
        Coco coco = Coco.ok;
        Response<?> response = null;
        try {
            User validate = validate(appid, userid,sign);

            if (StringUtils.isEmp(userid + "")) throw new TypeException("userid不能为空");
            if (StringUtils.isEmp(roleid + "")) throw new TypeException("roleid不能为空");

            userMagr.setUserRole(roleid,validate.getName());
        }catch (TypeException message) {
            coco.code = -100;
            coco.message = message.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
        }finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @GetMapping("/gets/{appid}/{userid}")
    @LogEs(url = "/user/gets",dec = "获取用户")
    public Response<?> getUser(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) String sign

    ){
        Coco coco = Coco.ok;
        List<User> user = null;
        Response<?> response = null;
        try {
//            validate(appid, userid, data,sign);
              user = userMagr.getUser();
        }catch (TypeException message) {
            coco.code = -100;
            coco.message = message.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
        }finally {
            response = new Response<>(coco, user);
        }
        return response;
    }


    @PostMapping("/delete/{appid}/{userid}")
    @LogEs(url = "/user/delete",dec = "用户注销")
    public Response<?> deleteUser(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) String sign
    ){
        Coco coco = Coco.ok;
        Response<?> response = null;
        try {
            User validate = validate(appid, userid,sign);
            if (validate == null) throw new TypeException("该用户不存在或已停用");
            userMagr.deleteUser(userid,validate.getName());
        }catch (TypeException message) {
            coco.code = -100;
            coco.message = message.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
        }finally {
            response = new Response<>(coco);
        }
        return response;
    }









}

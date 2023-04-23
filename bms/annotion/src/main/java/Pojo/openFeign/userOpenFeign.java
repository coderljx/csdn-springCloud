package Pojo.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "userService")
public interface userOpenFeign {

    /**
     * 通过用户id查询用户基本信息 和当前登录状态
     *
     * @param appid
     * @param userid
     * @return
     */
    @RequestMapping("/userService/api/user/getUserStatus/{appid}")
    String getUserByid(@PathVariable("appid") Integer appid,
                       @RequestParam("userid") Integer userid);


    /**
     * 获取这个用户的关注人列表
     * @param appid
     * @param userid
     * @return
     */
    @RequestMapping("/userService/api/user/getUserFollow/{appid}")
    String getUserFollow(@PathVariable("appid") Integer appid,
                         @RequestParam("userid") Integer userid);


}

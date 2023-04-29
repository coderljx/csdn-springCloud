package coderljxTitle.Api;

import Pojo.Consumer.Consumet;
import Pojo.DB.Module;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.MapUtils;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import coderljxTitle.Mgr.ClassMgr;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassService extends Validate {

    @Resource
    private ClassMgr moduleMgr;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/get/{appid}")
    @LogEs(url = "/class/get", dec = "获取当前所有模块")
    public Response<?> getModules(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid
    ) {

        return Consumet.Logic(() -> {
            validate(appid);

            return moduleMgr.queryModule();
        });
    }


    @PostMapping("/addModule/{appid}")
    @LogEs(url = "/class/addModule", dec = "新增系统模块")
    public Response<?> addModules(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        return Consumet.Logic(() -> {
            User user = validate(appid, userid, sign, data);
            Module module = MapUtils.MapToObject(data, Module.class);

            moduleMgr.addModules(module, user);
            return null;
        });
    }


    @GetMapping("/delModules/{appid}")
    @LogEs(url = "/class/delModules", dec = "删除系统模块")
    public Response<?> delModules(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return Consumet.Logic(() -> {
            User user = validate(appid, userid, sign);

            if (id == null || id <= 0) {
                throw new TypeException("E000003");
            }
            moduleMgr.delModules(id, user);
            return null;
        });
    }


    @GetMapping("/getLinks/{appid}")
    @LogEs(url = "/class/getLinks", dec = "获取广告的图片链接")
    public Response<?> getLinks(
            @PathVariable String appid,
            @RequestParam(value = "", required = false) String sign
    ) {

        return Consumet.Logic(() -> {
            validate(appid, sign);
            List<String> res = moduleMgr.getLinks();
            return res;
        });
    }


}

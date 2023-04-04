package coderljxTitle.Api;

import Pojo.DB.Coco;
import Pojo.DB.Module;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.MapUtils;
import Pojo.LjxUtils.UUID;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import coderljxTitle.Mgr.ClassMgr;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        List<Module> data = null;
        try {
            validate(appid);

            data = moduleMgr.queryModule();
            coco.code = 200;
            coco.message = "Success";
        } catch (Pojo.LjxEx.TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception message) {
            coco.code = -101;
            coco.message = message.getMessage();
        } finally {
            response = new Response<>(coco, data);
        }
        return response;
    }


    @PostMapping("/addModule/{appid}")
    @LogEs(url = "/class/addModule", dec = "新增系统模块")
    public Response<?> addModules(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, sign, data);
            Module module = MapUtils.MapToObject(data, Module.class);

            moduleMgr.addModules(module, user);
            coco.code = 200;
            coco.message = "Success";
        } catch (Pojo.LjxEx.TypeException message) {
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


    @GetMapping("/delModules/{appid}")
    @LogEs(url = "/class/delModules", dec = "删除系统模块")
    public Response<?> delModules(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestParam(value = "id", required = false) Integer id
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, sign);

            if (id == null || id <= 0) {
                throw new TypeException("E000003");
            }

            moduleMgr.delModules(id, user);
            coco.code = 200;
            coco.message = "Success";
        } catch (Pojo.LjxEx.TypeException message) {
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


    @GetMapping("/getLinks/{appid}")
    @LogEs(url = "/class/getLinks", dec = "获取广告的图片链接")
    public Response<?> getLinks(
            @PathVariable String appid,
            @RequestParam(value = "", required = false) String sign
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        List<String> res = new ArrayList<>();
        try {
            validate(appid, sign);
            res = moduleMgr.getLinks();

            coco.code = 200;
            coco.message = "Success";
        } catch (Pojo.LjxEx.TypeException message) {
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


}

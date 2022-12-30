package coderljxTitle.Api;

import Pojo.DB.Coco;
import Pojo.DB.Module;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxUtils.MapUtils;
import an.Log.LogEs;
import coderljxTitle.Mgr.ModuleMgr;
import com.codeljxUser.Validate;
import org.apache.ibatis.type.TypeException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassService extends Validate {

    @Resource
    private ModuleMgr moduleMgr;

    @GetMapping("/get/{appid}/{userid}")
    @LogEs(url = "/class/get",dec = "获取当前所有模块")
    public Response<?> getModules(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) String sign
    ){
        Coco coco = Coco.ok;
        Response<?> response = null;
        List<Module> data = null;
        try {
            validate(appid,userid,sign);
            data = moduleMgr.queryModule();
        }catch (TypeException message) {
            coco.code = -100;
            coco.message = message.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
        }finally {
            response = new Response<>(coco,data);
        }
        return response;
    }


    @PostMapping("/addModule/{appid}/{userid}")
    @LogEs(url = "/class/addModule",dec = "新增系统模块")
    public Response<?> addModules(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestBody String data
    ){
        Coco coco = Coco.ok;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, sign, data);
            Module module = MapUtils.MapToObject(data, Module.class);

            moduleMgr.addModules(module, user.getName());
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

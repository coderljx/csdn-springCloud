package Activities.Api;

import Activities.Mgr.ActivitiesMgr;
import Pojo.DB.Activities;
import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.UUID;
import Pojo.LjxUtils.Validate;
import Pojo.SearchArgsMap;
import an.Log.LogEs;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/active")
public class ActivitiesService extends Validate {

    @Resource
    private ActivitiesMgr activitiesMgr;

    @PostMapping("/getAll/{appid}")
    @LogEs(url = "/getAll",dec = "获取所有的活动信息")
    public Response<?> getAllActivities(
            @PathVariable String appid,
            @RequestParam(value = "userid",required = false) Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestBody String data
    ){
        User user = null;
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        List<Activities> res = new ArrayList<>();
        try {
            SearchArgsMap validate = validate(appid, data);

            res = activitiesMgr.getActive(validate.getPer_page(),validate.getCurr_page());

            coco.code = 200;
            coco.message = "Success";
        }catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
            e.printStackTrace();
        }finally {
            response = new Response<>(coco,res);
        }
        return response;
    }



    @PostMapping("/addActivities/{appid}")
    @LogEs(url = "/addActivities",dec = "新增一个活动信息")
    public Response<?> addActivities(
            @PathVariable String appid,
            @RequestParam(value = "userid",required = false) Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestBody String data
    ){
        User user = null;
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User validate = validate(appid, userid, data);
            activitiesMgr.addActivities(validate);

            coco.code = 200;
            coco.message = "Success";
        }catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
            e.printStackTrace();
        }finally {
            response = new Response<>(coco);
        }
        return response;
    }




    @PostMapping("/updateActivities/{appid}")
    @LogEs(url = "/addActivities",dec = "新增一个活动信息")
    public Response<?> updateActivities(
            @PathVariable String appid,
            @RequestParam(value = "userid",required = false) Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestBody String data
    ){
        User user = null;
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User validate = validate(appid, userid, data);
            activitiesMgr.updateActivities(validate);

            coco.code = 200;
            coco.message = "Success";
        }catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
            e.printStackTrace();
        }finally {
            response = new Response<>(coco);
        }
        return response;
    }




}

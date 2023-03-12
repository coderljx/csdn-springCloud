package Activities.Api;

import Activities.Mgr.ActivitiesMgr;
import Pojo.DB.Activities;
import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.UUID;
import Pojo.SearchArgsMap;
import an.Log.LogEs;
import com.codeljxUser.Validate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hd")
public class ActivitiesService extends Validate {

    @Resource
    private ActivitiesMgr activitiesMgr;

    @PostMapping("/getAll/{appid}")
    @LogEs(url = "/hd/getAll",dec = "获取所有的活动信息")
    public Response<?> getAllActivities(
            @PathVariable String appid,
            @RequestParam(value = "userid",required = false) Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestBody String data
    ){
        User user = null;
        Coco coco = null;
        Response<?> response = null;
        List<Activities> res = new ArrayList();
        try {
            SearchArgsMap validate = validate(appid, data);

            res = activitiesMgr.getActive(validate.getPer_page(),validate.getCurr_page());

            coco = Coco.ok;
        }catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (DataException dataException) {
            coco = Coco.InitCoco;
            coco.code = -102;
            coco.message = dataException.getMessage();
        }catch (Exception e){
            coco = Coco.InitCoco;
            coco.code = -101;
            coco.message = e.getMessage();
            e.printStackTrace();
        }finally {
            response = new Response<>(coco,res);
        }
        return response;
    }



    @PostMapping("/addActivities/{appid}")
    @LogEs(url = "/hd/addActivities",dec = "新增一个活动信息")
    public Response<?> addActivities(
            @PathVariable String appid,
            @RequestParam(value = "userid",required = false) Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestBody String data
    ){
        User user = null;
        Coco coco = null;
        Response<?> response = null;
        List<Activities> res = new ArrayList();
        try {
            SearchArgsMap validate = validate(appid, data);



            coco = Coco.ok;
        }catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (DataException dataException) {
            coco = Coco.InitCoco;
            coco.code = -102;
            coco.message = dataException.getMessage();
        }catch (Exception e){
            coco = Coco.InitCoco;
            coco.code = -101;
            coco.message = e.getMessage();
            e.printStackTrace();
        }finally {
            response = new Response<>(coco,res);
        }
        return response;
    }


}

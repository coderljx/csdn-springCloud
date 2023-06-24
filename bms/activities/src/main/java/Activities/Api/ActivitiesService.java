package Activities.Api;

import Activities.Mgr.ActivitiesMgr;
import Pojo.Consumer.Consumet;
import Pojo.DB.Activities;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxUtils.Validate;
import Pojo.SearchArgsMap;
import an.Log.LogEs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/active")
@Api(value = "活动接口",tags = "活动测试接口")
public class ActivitiesService extends Validate {

    @Resource
    private ActivitiesMgr activitiesMgr;

    @PostMapping("/getAll/{appid}")
    @LogEs(url = "/getAll", dec = "获取所有的活动信息")
    @ApiOperation(value = "获取所有活动")
    public Response<?> getAllActivities(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {
        return Consumet.Logic(() -> {
            SearchArgsMap validate = validate(appid, data);

            List<Activities> res = activitiesMgr.getActive(validate.getPer_page(), validate.getCurr_page());

            return res;
        });

    }


    @PostMapping("/addActivities/{appid}")
    @LogEs(url = "/addActivities", dec = "新增一个活动信息")
    @ApiOperation(value = "新增一个活动信息")
    public Response<?> addActivities(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {

        return Consumet.Logic(() -> {
            User validate = validate(appid, userid, data);
            activitiesMgr.addActivities(validate);

            return null;
        });
    }


    @PostMapping("/updateActivities/{appid}")
    @LogEs(url = "/addActivities", dec = "新增一个活动信息")
    @ApiOperation(value = "新增一个活动信息")
    public Response<?> updateActivities(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestBody String data
    ) {

        return Consumet.Logic(() -> {
            User validate = validate(appid, userid, data);
            activitiesMgr.updateActivities(validate);

            return null;
        });
    }


}

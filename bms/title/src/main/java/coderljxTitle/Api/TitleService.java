package coderljxTitle.Api;

import Pojo.Consumer.Consumet;
import Pojo.DB.Response;
import Pojo.DB.Title;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import coderljxTitle.Mgr.TitleMgr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("title")
public class TitleService extends Validate {

    @Value("${fileImg}")
    private String imgUrl;

    @Resource
    private TitleMgr titleMgr;

    @GetMapping("/getTitle/{appid}")
    @LogEs(url = "/title/getTitle", dec = "获取模块下的标签")
    public Response<?> getTitle(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "id", required = false) Integer moduleID
    ) {

        return Consumet.Logic(() -> {
            User user = validate(appid, userid, sign);

            if (moduleID == null || moduleID <= 0) {
                throw new DataException("moduleID 不可为空");
            }

            List<Title> titleList = titleMgr.getTitleByModule(moduleID);
            return titleList;
        });
    }


    @PostMapping("/addTitle/{appid}")
    @LogEs(url = "/title/addTitle", dec = "新增模块下的标签")
    public Response<?> addTitle(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "moduleId", required = false) Integer moduleId,
            @RequestBody String data
    ) {

        return Consumet.Logic(() -> {
            User user = validate(appid, userid, data);

            if (moduleId == null || moduleId <= 0) {
                throw new DataException("moduleId");
            }

            titleMgr.addTitle(user, moduleId);
            return null;
        });
    }


}

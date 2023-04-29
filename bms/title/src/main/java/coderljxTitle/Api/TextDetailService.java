package coderljxTitle.Api;

import Pojo.Consumer.Consumet;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import coderljxTitle.Mgr.TextDetailMgr;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/textDetail")
public class TextDetailService extends Validate {

    @Resource
    private TextDetailMgr textDetailMgr;


    @PostMapping("/addTextDetail/{appid}")
    @LogEs(url = "/textDetail/addTextDetail", dec = "新增文章的点赞等信息")
    public Response<?> addTextDetail(
            @PathVariable String appid,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestBody String data
    ) {

        return Consumet.Logic(() -> {
            User user = validate(appid, userid, data);

            textDetailMgr.addTextDetail(user);
            return null;
        });
    }


    @PostMapping("/setTextDetail/{appid}")
    @LogEs(url = "/textDetail/setTextDetail", dec = "修改文章的点赞等信息")
    public Response<?> updateTextDetail(
            @PathVariable String appid,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestBody String data
    ) {

        return Consumet.Logic(() -> {
            User user = validate(appid, userid, data);

            textDetailMgr.updateTextDetail(user);
            return null;
        });
    }


    @DeleteMapping("/delTextDetail/{appid}")
    @LogEs(url = "/textDetail/delTextDetail", dec = "删除用户对当前文章的点赞等信息")
    public Response<?> delTextDetail(
            @PathVariable String appid,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "textId", required = false) Integer textId
    ) {

        return Consumet.Logic(() -> {
            User user = validate(appid, userid);

            textDetailMgr.delTextDetail(user, textId);
            return null;
        });
    }


}

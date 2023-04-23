package coderljxTitle.Api;

import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxUtils.UUID;
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
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, data);

            textDetailMgr.addTextDetail(user);

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




    @PostMapping("/setTextDetail/{appid}")
    @LogEs(url = "/textDetail/setTextDetail", dec = "修改文章的点赞等信息")
    public Response<?> updateTextDetail(
            @PathVariable String appid,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestBody String data
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, data);

            textDetailMgr.updateTextDetail(user);

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



    @DeleteMapping("/delTextDetail/{appid}")
    @LogEs(url = "/textDetail/delTextDetail", dec = "删除用户对当前文章的点赞等信息")
    public Response<?> delTextDetail(
            @PathVariable String appid,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "textId", required = false) Integer textId
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User user = validate(appid, userid);

            textDetailMgr.delTextDetail(user,textId);

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




}

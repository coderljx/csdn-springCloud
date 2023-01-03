package coderljxTitle.Api;

import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.DB.Title;
import Pojo.DB.User;
import an.Log.LogEs;
import com.alibaba.fastjson2.JSONObject;
import com.codeljxUser.Validate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("title")
public class TitleService extends Validate {

    @GetMapping("/getTitle/{appid}/{userid}")
    @LogEs(url = "/title/getTitle",dec = "获取模块下的标签")
    public Response<?> getTitle(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestParam(value = "id",required = false) Integer moduleID
    ){
        Coco coco = Coco.ok;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, sign,moduleID + "");


        }catch (Pojo.LjxEx.TypeException message) {
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


    @PostMapping("/addTitle/{appid}/{userid}")
    @LogEs(url = "/title/addTitle",dec = "新增模块下的标签")
    public Response<?> addTitle(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestBody String data
    ){
        Coco coco = Coco.ok;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, sign);

            Title title = JSONObject.parseObject(data, Title.class);



        }catch (Pojo.LjxEx.TypeException message) {
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

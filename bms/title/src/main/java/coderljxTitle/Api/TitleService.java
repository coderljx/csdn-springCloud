package coderljxTitle.Api;

import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.DB.Title;
import Pojo.DB.User;
import Pojo.LjxUtils.FileUtils;
import Pojo.LjxUtils.UUID;
import an.Log.LogEs;
import com.alibaba.fastjson2.JSONObject;
import com.codeljxUser.Validate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
        Coco coco = null;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, sign,moduleID + "");

            coco = Coco.ok;
        }catch (Pojo.LjxEx.TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (Exception e){
            coco = Coco.InitCoco;
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
        Coco coco = null;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, sign);

            Title title = JSONObject.parseObject(data, Title.class);

            coco = Coco.ok;
        }catch (Pojo.LjxEx.TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (Exception e){
            coco = Coco.InitCoco;
            coco.code = -101;
            coco.message = e.getMessage();
        }finally {
            response = new Response<>(coco);
        }
        return response;
    }

    @GetMapping("/viewImg/{url}")
    @LogEs(url = "/title/viewImg",dec = "查看图片")
    public void viewImg(
            @PathVariable("url") String url,
            @RequestParam(value = "",required = false) String sign,
            HttpServletResponse httpServletResponse
    ){
        try {
            System.out.println(url);
//            httpServletResponse.setHeader("Content-Type","application/x-img");
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            byte[] bytes = FileUtils.readFile("C:" + url);
            outputStream.write(bytes);

        }catch (Pojo.LjxEx.TypeException message) {
        }catch (Exception e){
        }
    }


}

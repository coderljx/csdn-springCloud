package coderljxTitle.Api;

import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.DB.Title;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxUtils.FileUtils;
import Pojo.LjxUtils.UUID;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import coderljxTitle.Mgr.TitleMgr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        List<Title> titleList = null;
        try {
            User user = validate(appid, userid, sign);

            if (moduleID == null || moduleID <= 0) {
                throw new DataException("moduleID 不可为空");
            }

            titleList = titleMgr.getTitleByModule(moduleID);

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
            response = new Response<>(coco,titleList);
        }
        return response;
    }


    @PostMapping("/addTitle/{appid}")
    @LogEs(url = "/title/addTitle", dec = "新增模块下的标签")
    public Response<?> addTitle(
            @PathVariable String appid,
            @RequestParam(value = "userid",required = false) Integer userid,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "moduleId", required = false) Integer moduleId,
            @RequestBody String data
    ) {
        Coco coco = Coco.InitCoco;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, data);

            if (moduleId == null || moduleId <= 0) {
                throw new DataException("moduleId");
            }

            titleMgr.addTitle(user,moduleId);

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


    @GetMapping("/viewImg/{url}")
    @LogEs(url = "/title/viewImg", dec = "查看图片")
    public void viewImg(
            @PathVariable("url") String url,
            HttpServletResponse httpServletResponse
    ) {
        try {
            System.out.println(url);
//            httpServletResponse.setHeader("Content-Type","application/x-img");
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            byte[] bytes = FileUtils.readFile(imgUrl + url);
            outputStream.write(bytes);
        } catch (Pojo.LjxEx.TypeException message) {
        } catch (Exception e) {
        }
    }


}

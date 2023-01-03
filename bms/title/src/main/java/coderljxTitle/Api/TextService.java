package coderljxTitle.Api;

import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.DB.Text;
import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.StringUtils;
import an.Log.LogEs;
import coderljxTitle.Mgr.TextMgr;
import com.codeljxUser.Validate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/text")
public class TextService extends Validate {

    @Resource
    private TextMgr textMgr;

    @PostMapping("/addText/{appid}/{userid}")
    @LogEs(url = "/text/addText",dec = "新增用户文章")
    public Response<?> addText(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestParam(value = "detail",required = false) String detail,
            @RequestParam(value = "coverPhoto",required = false) MultipartFile[] coverPhoto,
            @RequestParam(value = "imageStr",required = false) MultipartFile[] file,
            @RequestParam(value = "titleStr",required = false) String titleStr,
            @RequestParam(value = "textType",required = false) Character textType,
            @RequestParam(value = "releaseForm",required = false) Character releaseForm,
            @RequestParam(value = "contextLevel",required = false) Character contextLevel,
            @RequestParam(value = "coverTitle",required = false) String coverTitle
    ){
        Coco coco = Coco.ok;
        Response<?> response = null;
        try {
            if (StringUtils.isEmp(titleStr)) throw new TypeException("titleStr 不可为空");
            if (StringUtils.isEmp(String.valueOf(textType))) throw new TypeException("textType 不可为空");
            if (StringUtils.isEmp(String.valueOf(contextLevel))) throw new TypeException("contextLevel 不可为空");
            if (StringUtils.isEmp(coverTitle)) throw new TypeException("coverTitle 不可为空");

            validate(appid, userid, sign);
            textMgr.addUserText(detail,userid,coverPhoto,titleStr,textType,releaseForm,contextLevel,file,coverTitle);
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

    @GetMapping("/get/{appid}/{userid}")
    @LogEs(url = "/text/get",dec = "获取用户文章")
    public Response<?> getText(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestBody String data
    ){
        Coco coco = Coco.ok;
        Response<?> response = null;
        List<Text> textList = null;
        try {
            validate(appid, userid, sign, data);
            textList = textMgr.getUserText(userid);
        }catch (Pojo.LjxEx.TypeException message) {
            coco.code = -100;
            coco.message = message.getMessage();
        }catch (Exception e){
            coco.code = -101;
            coco.message = e.getMessage();
        }finally {
            response = new Response<>(coco,textList);
        }
        return response;
    }

    @GetMapping("/deleteText/{appid}/{userid}")
    @LogEs(url = "/text/deleteText",dec = "用户删除文章")
    public Response<?> deleteText(
            @PathVariable String appid,
            @PathVariable Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestParam(value = "id",required = false) Integer id
    ){
        Coco coco = Coco.ok;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, sign);

            textMgr.deleteUserText(id, user.getName());


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

package coderljxTitle.Api;

import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.DB.Text;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxUtils.StringUtils;
import Pojo.LjxUtils.UUID;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import coderljxTitle.Mgr.TextMgr;
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
        Coco coco = null;
        Response<?> response = null;
        try {
            if (StringUtils.isEmp(titleStr)) {
                throw new DataException("titleStr 不可为空");
            }
            if (StringUtils.isEmp(String.valueOf(textType))) {
                throw new DataException("textType 不可为空");
            }
            if (StringUtils.isEmp(String.valueOf(contextLevel))) {
                throw new DataException("contextLevel 不可为空");
            }
            if (StringUtils.isEmp(coverTitle)) {
                throw new DataException("coverTitle 不可为空");
            }

            validate(appid, userid, sign);
            textMgr.addUserText(detail,userid,coverPhoto,titleStr,textType,releaseForm,contextLevel,file,coverTitle);
            coco = Coco.ok;
        }catch (Pojo.LjxEx.TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (DataException dataException) {
            coco = Coco.InitCoco;
            coco.code = -102;
            coco.message = dataException.getMessage();
        }catch (Exception e){
            coco = Coco.InitCoco;
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
        Coco coco = null;
        Response<?> response = null;
        List<Text> textList = null;
        try {
            validate(appid, userid, sign, data);
            textList = textMgr.getUserText(userid);
            coco = Coco.ok;
        }catch (Pojo.LjxEx.TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (DataException dataException) {
            coco = Coco.InitCoco;
            coco.code = -102;
            coco.message = dataException.getMessage();
        }catch (Exception e){
            coco = Coco.InitCoco;
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
        Coco coco = null;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, sign);

            textMgr.deleteUserText(id, user.getName());

            coco = Coco.ok;
        }catch (Pojo.LjxEx.TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (DataException dataException) {
            coco = Coco.InitCoco;
            coco.code = -102;
            coco.message = dataException.getMessage();
        }catch (Exception e){
            coco = Coco.InitCoco;
            coco.code = -101;
            coco.message = e.getMessage();
        }finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @GetMapping("/getAvdText/{appid}")
    @LogEs(url = "/text/getAvdText",dec = "获取头条，热点，广告等信息")
    public Response<?> getAvdText(
            @PathVariable String appid,
            @RequestParam(value = "userid",required = false) Integer userid,
            @RequestParam(value = "",required = false) String sign,
            @RequestParam(value = "id",required = false) Integer id
    ){
        Coco coco = null;
        Response<?> response = null;
        try {
            User user = validate(appid, userid, sign);

            textMgr.getAvdText();
            coco = Coco.ok;
        }catch (Pojo.LjxEx.TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (DataException dataException) {
            coco = Coco.InitCoco;
            coco.code = -102;
            coco.message = dataException.getMessage();
        }catch (Exception e){
            coco = Coco.InitCoco;
            coco.code = -101;
            coco.message = e.getMessage();
        }finally {
            response = new Response<>(coco);
        }


        return response;
    }


    @GetMapping("/getFollowedText/{appid}")
    @LogEs(url = "/text/getFollowedText",dec = "获取关注的人发布的文章")
    public Response<?> getFollowedText(
            @PathVariable String appid,
            @RequestParam(value = "userid",required = false) Integer userid,
            @RequestParam(value = "",required = false) String sign
    ) {
        Coco coco = null;
        Response<?> response = null;
        List<Text> followedText = null;
        try {
            User user = validate(appid, userid, sign);

            followedText = textMgr.getFollowedText(userid);
            coco = Coco.ok;
        }catch (Pojo.LjxEx.TypeException message) {
            coco = UUID.ExceptionFill(message);
        }catch (DataException dataException) {
            coco = Coco.InitCoco;
            coco.code = -102;
            coco.message = dataException.getMessage();
        }catch (Exception e){
            coco = Coco.InitCoco;
            coco.code = -101;
            coco.message = e.getMessage();
        }finally {
            response = new Response<>(coco,followedText);
        }
        return response;
    }




}

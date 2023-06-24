package coderljxTitle.Api;

import Pojo.Consumer.Consumet;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import coderljxTitle.Mgr.TextMgr;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/text")
public class TextService extends Validate {

    @Resource
    private TextMgr textMgr;

    @PostMapping("/addText/{appid}")
    @LogEs(url = "/text/addText", dec = "新增用户文章")
    public Response<?> addText(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestParam(value = "detail", required = false) String detail,
            @RequestParam(value = "coverPhoto", required = false) MultipartFile[] coverPhoto,
            @RequestParam(value = "imageStr", required = false) MultipartFile[] file,
            @RequestParam(value = "titleStr", required = false) String titleStr,
            @RequestParam(value = "textType", required = false) Character textType,
            @RequestParam(value = "releaseForm", required = false) Character releaseForm,
            @RequestParam(value = "contextLevel", required = false) Character contextLevel,
            @RequestParam(value = "coverTitle", required = false) String coverTitle
    ) {

        return Consumet.Logic(() -> {
            textMgr.addUserText(appid, detail, userid, coverPhoto, titleStr, textType, releaseForm, contextLevel, file, coverTitle);
            return null;
        });
    }

    @GetMapping("/get/{appid}")
    @LogEs(url = "/text/get", dec = "根据文章id获取文章")
    public Response<?> getText(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "id", required = false) Integer id
    ) {

        return Consumet.Logic(() -> textMgr.getUserText(userid, id));
    }


    @GetMapping("/deleteText/{appid}")
    @LogEs(url = "/text/deleteText", dec = "用户删除文章")
    public Response<?> deleteText(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestParam(value = "id", required = false) Integer id
    ) {

        return Consumet.Logic(() -> {
            User user = validate(appid, userid, sign);

            textMgr.deleteUserText(id, user);
            return null;
        });
    }


    @GetMapping("/getAvdText/{appid}")
    @LogEs(url = "/text/getAvdText", dec = "获取头条，热点，广告等信息")
    public Response<?> getAvdText(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "", required = false) String sign,
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return Consumet.Logic(() -> {
            User user = validate(appid, userid, sign);

            textMgr.getAvdText();
            return null;
        });
    }


    @GetMapping("/getFollowedText/{appid}")
    @LogEs(url = "/text/getFollowedText", dec = "获取关注的人发布的文章")
    public Response<?> getFollowedText(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "sign", required = false) String sign
    ) {

        return Consumet.Logic(() -> {
            User user = validate(appid, userid);

            return textMgr.getFollowedText(user);
        });
    }


    @GetMapping("/getHotText/{appid}")
    @LogEs(url = "/text/getHotText", dec = "获取热点文章")
    public Response<?> getHotText(
            @PathVariable String appid,
            @RequestParam(value = "sign", required = false) String sign
    ) {

        return Consumet.Logic(() -> {
            validate(appid);

            return textMgr.getHotText();
        });
    }


    @GetMapping("/getTextByUid/{appid}")
    @LogEs(url = "/text/getTextByUid", dec = "根据文章id,获取用户信息")
    public Response<?> getTextByUid(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "textId", required = false) Integer textId
    ) {

        return Consumet.Logic(() -> {
            validate(appid);

            return textMgr.getTextByUid(userid, textId);
        });
    }



    @PostMapping("/addTextLike/{appid}")
    @LogEs(url = "/text/addTextLike", dec = "给文章点赞")
    public Response<?> addTextLike(
            @PathVariable String appid,
            @RequestParam(value = "userid", required = false) Integer userid,
            @RequestParam(value = "sign", required = false) String sign,
            @RequestParam(value = "textId", required = false) Integer textId
    ) {

        return Consumet.Logic(() -> {
            validate(appid);

            return null;
        });
    }


}

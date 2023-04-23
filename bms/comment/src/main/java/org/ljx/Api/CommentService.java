package org.ljx.Api;

import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.DB.User;
import Pojo.LjxEx.DataException;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.UUID;
import Pojo.LjxUtils.Validate;
import an.Log.LogEs;
import org.ljx.Mgr.CommentMgr;
import org.ljx.Vo.CommentVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentService extends Validate {

    @Resource
    private CommentMgr commentMgr;

    @PostMapping("/saveComment/{appid}")
    @LogEs(url = "comment/saveComment", dec = "用户编写评论")
    public Response saveComment(@PathVariable("appid") String appid,
                                @RequestParam(value = "userid", required = false) Integer userid,
                                @RequestBody String data) {
        Coco coco = Coco.InitCoco;
        coco.message = "success";
        coco.code = 200;
        Response<?> response = null;
        try {

            User validate = validate(appid, userid, data);

            commentMgr.saveCommentCount(validate);

        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
            e.printStackTrace();
        } finally {
            response = new Response<>(coco);
        }
        return response;
    }


    @GetMapping("/getCommentById/{appid}")
    @LogEs(url = "comment/getCommentById", dec = "根据文章id,获取该文章下的评论总数")
    public Response getCommentById(@PathVariable("appid") String appid,
                                   @RequestParam(value = "userid", required = false) Integer userid,
                                   @RequestParam(value = "textId",required = false) Integer textId,
                                   @RequestParam(value = "perPage",required = false) Integer perPage,
                                   @RequestParam(value = "currPage",required = false) Integer currPage) {
        Coco coco = Coco.InitCoco;
        coco.message = "success";
        coco.code = 200;
        Response<?> response = null;
        CommentVo commentById = null;
        try {
            User user = validate(appid,userid);
            commentById = commentMgr.getCommentById(user, textId, perPage, currPage);


        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
            e.printStackTrace();
        } finally {
            response = new Response<>(coco,commentById);
        }
        return response;
    }


}

package org.ljx.Api;

import Pojo.Consumer.Consumet;
import Pojo.DB.Response;
import Pojo.DB.User;
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
    public Response<?> saveComment(@PathVariable("appid") String appid,
                                   @RequestParam(value = "userid", required = false) Integer userid,
                                   @RequestBody String data) {

        return Consumet.Logic(() -> {
            User validate = validate(appid, userid, data);

            commentMgr.saveCommentCount(validate);

            return null;
        });
    }


    @GetMapping("/getCommentById/{appid}")
    @LogEs(url = "comment/getCommentById", dec = "根据文章id,获取该文章下的评论总数")
    public Response<?> getCommentById(@PathVariable("appid") String appid,
                                      @RequestParam(value = "userid", required = false) Integer userid,
                                      @RequestParam(value = "textId", required = false) Integer textId,
                                      @RequestParam(value = "perPage", required = false) Integer perPage,
                                      @RequestParam(value = "currPage", required = false) Integer currPage) {

        return Consumet.Logic(() -> {
            User user = validate(appid, userid);
            CommentVo commentById = commentMgr.getCommentById(user, textId, perPage, currPage);

            return commentById;
        });
    }


    @PostMapping("/setCommentLike/{appid}")
    @LogEs(url = "comment/setCommentLike", dec = "用户点赞或者取消点赞评论")
    public Response<?> setCommentLike(@PathVariable("appid") String appid,
                                      @RequestParam(value = "userid", required = false) Integer userid,
                                      @RequestParam(value = "textid", required = false) Integer textId,
                                      @RequestParam(value = "commentid", required = false) String commentId) {

        return Consumet.Logic(() -> {
            User user = validate(appid, userid);
            commentMgr.setCommentLike(user, commentId, textId);

            return null;
        });
    }


}

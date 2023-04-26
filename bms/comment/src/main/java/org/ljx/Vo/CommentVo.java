package org.ljx.Vo;

import example.Pojo.Comment;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CommentVo {

    private  List<String> foors; // 顶层评论的数据
    private Map<String, List<Comment>> listMap; // 根据顶层评论区分的所有评论


    private Likes likes;
    private long total; // 整个es库中的数据总数



    @Data
    public static class Likes {
        private Map<Integer,List<String>> likes; //当前用户都给文章下的那些评论点过赞，如果不传用户id，该值为空
        private Map<String,Long> commenLikes;// 评论的点赞详情
    }
}

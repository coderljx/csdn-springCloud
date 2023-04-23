package org.ljx.Vo;

import example.Pojo.Comment;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CommentVo {

    private  List<String> foors; // 顶层评论的数据
    private Map<String, List<Comment>> listMap; // 根据顶层评论区分的所有评论

    private long total; // 整个es库中的数据总数
}

package org.ljx.Mgr;

import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.ResponseParse;
import Pojo.LjxUtils.StringUtils;
import Pojo.LjxUtils.TimeUtil;
import Pojo.LjxUtils.UUID;
import Pojo.SearchArgsMap;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import example.EsTemplate;
import example.Pojo.Comment;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.ljx.Vo.CommentVo;
import org.ljx.config.IndexConfig;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class CommentMgr {

    @Resource
    private Pojo.openFeign.userOpenFeign userOpenFeign;

    @Resource
    private EsTemplate esTemplate;

    private String getUserName(User user) {
        String userByid = userOpenFeign.getUserByid(user.getAppId(), user.getId());
        return ResponseParse.getUserName(userByid, "name");
    }

    /**
     * 新增一条评论
     *
     * @param user
     */
    public void saveCommentCount(User user) throws Exception {

        SearchArgsMap searchArgsMap = user.getSearchArgsMap();
        Object object = searchArgsMap.getObject();
        if (object instanceof Map) {
            Map<?, ?> json = (Map<?, ?>) object;
            Comment comment = JSONObject.parseObject(JSON.toJSONString(json), Comment.class);
            /**
             *     如果该条数据的父id为空，代表该评论是顶层评论，顶层评论会默认添加一个uuid的楼层id
             *     之后所有的评论都会标注这个楼层id，代表是该楼层下的评论数据
             */
            if (StringUtils.isEmp(comment.getParentComentId())) {
                comment.setFoor(UUID.getUUID());
            }
            comment.setIsDelete(0);
            comment.setCreateTime(TimeUtil.Parselong(new Date()));
            esTemplate.save(comment, IndexConfig.CommentIndexName);
        } else {
            throw new TypeException("E000001_10");
        }
    }

    /**
     * 获取文章下的评论
     */
    public CommentVo getCommentById(User user, Integer textId, Integer perPage, Integer currPage) {
        if (textId == null || textId <= 0) throw new TypeException("E0000003_001");
        CommentVo commentVo = new CommentVo();
        BoolQueryBuilder totalDataCount = QueryBuilders.boolQuery();
        totalDataCount.must(QueryBuilders.termQuery("textId", textId));
        totalDataCount.must(QueryBuilders.termQuery("isDelete", 0));
        NativeSearchQuery totalData = esTemplate.genNativeSearchQuery(totalDataCount);
        SearchHits<Comment> totalComment = esTemplate.search(totalData, Comment.class, "comment");
        long totalHits = totalComment.getTotalHits();
        commentVo.setTotal(totalHits);
        // 如果整个es库中没有查到该文章下的评论 表示该文章没有评论 直接返回即可
        if (totalHits == 0) {
            return commentVo;
        }


        /**
         * 第一步 ：
         * 根据分页字段以及 文章id 查询顶层评论
         * 顶层评论 ： 首先根据textid 筛选出该文章下的数据
         *           再根据是否存在父评论id筛选（存在父评论的数据表示不是顶层评论，不存在的表示是顶层评论）
         */
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        TermQueryBuilder textId1 = QueryBuilders.termQuery("textId", textId);
        TermQueryBuilder isDelete = QueryBuilders.termQuery("isDelete", 0);
        boolQueryBuilder.must(textId1);
        boolQueryBuilder.must(isDelete);
        ExistsQueryBuilder parentComentId = QueryBuilders.existsQuery("parentComentId");
        boolQueryBuilder.mustNot(parentComentId);
        NativeSearchQuery nativeSearchQuery = esTemplate.genNativeSearchQuery(boolQueryBuilder, esTemplate.page(currPage, perPage));
        SearchHits<Comment> comment = esTemplate.search(nativeSearchQuery, Comment.class, "comment");

        List<SearchHit<Comment>> searchHits = comment.getSearchHits();
        // 该数据都是顶层评论的数据，接下来需要根据顶层评论的楼层id 查询出该楼层下的所有评论数据
        List<String> data = new CopyOnWriteArrayList<>();
        searchHits.forEach(item -> {
            data.add(item.getContent().getFoor());
        });
        //
        if (data.size() == 0) {
            return commentVo;
        }
        /**
         * 第二部 ： 根据顶层评论的foor 查询下面的所有数据 再进行分组
         * foorQuery : 所有的顶层评论之下的评论数据都再此
         *  必须是该顶层评论下的数据 并且是没有被删除的（评论会加入审核功能）
         */
        BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery();
        TermsQueryBuilder foor = QueryBuilders.termsQuery("foor", data);
        boolQueryBuilder2.must(foor);
        boolQueryBuilder2.must(isDelete);

        NativeSearchQuery foorQuery = esTemplate.genNativeSearchQuery(boolQueryBuilder2,
                SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        SearchHits<Comment> foorData = esTemplate.search(foorQuery, Comment.class, "comment");
        List<SearchHit<Comment>> searchHits1 = foorData.getSearchHits();
        List<Comment> foorDataList = new CopyOnWriteArrayList<>();
        searchHits1.forEach(item -> {
            foorDataList.add(item.getContent());
        });
        Map<String, List<Comment>> collect = foorDataList.stream().collect(Collectors.groupingBy(Comment::getFoor));

        commentVo.setFoors(data);
        commentVo.setListMap(collect);
        return commentVo;
    }


}

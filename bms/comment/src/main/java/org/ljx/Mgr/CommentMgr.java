package org.ljx.Mgr;

import Pojo.DB.User;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.UUID;
import Pojo.LjxUtils.*;
import Pojo.SearchArgsMap;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import example.EsTemplate;
import example.Index.IndexName;
import example.Pojo.Comment;
import example.Pojo.Like;
import lombok.SneakyThrows;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.ljx.Vo.CommentVo;
import org.ljx.config.IndexConfig;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class CommentMgr {

    @Resource
    private Pojo.openFeign.userOpenFeign userOpenFeign;

    @Resource
    private EsTemplate esTemplate;

    private String getUserName(User user) {
        if (user.getId() == null || user.getId() <= 0) throw new TypeException("E000001_02");

        String userByid = userOpenFeign.getUserByid(user.getAppId(), user.getId());
        return ResponseParse.getUserName(userByid, "name");
    }

    /**
     * 新增一条评论
     *
     * @param user
     */
    @SneakyThrows
    public void saveCommentCount(User user) {
        // 验证用户是否登录，不登录的用户允许评论
        getUserName(user);
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
            comment.setCreateTime(TimeUtil.ParseDate(new Date(), 1));
            esTemplate.save(comment, IndexConfig.CommentIndexName);
        } else {
            throw new TypeException("E000001_10");
        }
    }

    /**
     * 获取文章下的评论
     */
    @SneakyThrows
    public CommentVo getCommentById(User user, Integer textId, Integer perPage, Integer currPage) {
        if (textId == null || textId <= 0) throw new TypeException("E0000003_001");
        CommentVo commentVo = new CommentVo();
        BoolQueryBuilder totalDataCount = QueryBuilders.boolQuery();
        totalDataCount.must(QueryBuilders.termQuery("textId", textId));
        totalDataCount.must(QueryBuilders.termQuery("isDelete", 0));
        NativeSearchQuery totalData = esTemplate.genNativeSearchQuery(totalDataCount);
        SearchHits<Comment> totalComment = esTemplate.search(totalData, Comment.class, IndexName.Comment);
        long totalHits = totalComment.getTotalHits();
        commentVo.setTotal(totalHits);
        // 如果整个es库中没有查到该文章下的评论 表示该文章没有评论 直接返回即可
        if (totalHits == 0) {
            return commentVo;
        }
        // 开另一个线程去查询点赞索引库，主线程继续查询评论相关信息
        Future<CommentVo.Likes> invoke = ThreadUtils.invoke(() -> {
            Map<String, Long> likeMap = new HashMap<>();
            Map<Integer, List<String>> userList = new HashMap<>();
            List<String> lists = new CopyOnWriteArrayList<>();
            CommentVo.Likes likes = new CommentVo.Likes();
            TermQueryBuilder textId1 = QueryBuilders.termQuery("textId", textId);
            NativeSearchQuery total = new NativeSearchQueryBuilder()
                    .withQuery(textId1)
                    .addAggregation(new TermsAggregationBuilder("total", ValueType.NUMBER).field("parentComentId.keyword"))
                    .build();

            SearchHits<Like> totalLike = esTemplate.search(total, Like.class, IndexName.Like);
            // 如果这个评论下没有点赞信息
            if (totalLike.getTotalHits() == 0) {
                return likes;
            }

            Aggregation total1 = totalLike.getAggregations().get("total");
            if (total1 instanceof ParsedStringTerms) {
                ParsedStringTerms parsedStringTerms = (ParsedStringTerms) total1;
                List<? extends Terms.Bucket> buckets = parsedStringTerms.getBuckets();
                // 因为设置的是 根据 评论id分组，所以这里的key就是评论的id
                // 这样我们就遍历拿到了每个评论的数据数量，也就是有多少用户点过赞
                for (Terms.Bucket bucket : buckets) {
                    String parentComentId = (String) bucket.getKey();
                    long count = bucket.getDocCount();
                    likeMap.put(parentComentId, count);
                }
            }
            Integer id = user.getId();
            // 只有用户id不为空的情况下，我们才遍历当前登录用户有没有给评论点过赞
            if (id != null && id > 0) {
                List<SearchHit<Like>> searchHits = totalLike.getSearchHits();
                for (SearchHit<Like> searchHit : searchHits) {
                    if (Objects.equals(searchHit.getContent().getUserId(), id)) {
                        String parentComentId = searchHit.getContent().getParentComentId();
                        lists.add(parentComentId);
                    }
                }
                userList.put(id, lists);
            }
            likes.setCommenLikes(likeMap);
            likes.setLikes(userList);
            return likes;
        });


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
        SearchHits<Comment> comment = esTemplate.search(nativeSearchQuery, Comment.class, IndexName.Comment);

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
        SearchHits<Comment> foorData = esTemplate.search(foorQuery, Comment.class, IndexName.Comment);
        List<SearchHit<Comment>> searchHits1 = foorData.getSearchHits();
        List<Comment> foorDataList = new CopyOnWriteArrayList<>();
        searchHits1.forEach(item -> {
            foorDataList.add(item.getContent());
        });
        Map<String, List<Comment>> collect = foorDataList.stream().collect(Collectors.groupingBy(Comment :: getFoor));


        commentVo.setFoors(data);
        commentVo.setListMap(collect);
        commentVo.setLikes(invoke.get());
        return commentVo;
    }


    /**
     * 设置或者取消用户的点赞行为
     * 如果用户对该评论进行过点赞，那么再次调用该接口就是取消点赞
     * 如果没有点过赞，那么调用该接口就是点赞了
     */
    @SneakyThrows
    public void setCommentLike(User user, String commentId, Integer textId) {
        if (StringUtils.isEmp(textId)) throw new TypeException("E0000003_001");
        if (StringUtils.isEmp(commentId)) throw new TypeException("E0000003_002");

        getUserName(user);
        boolean likeIndex = esTemplate.indexInclude(IndexName.Like);
        // 先检查是否存在该索引库
        if (likeIndex) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            TermQueryBuilder userId = QueryBuilders.termQuery("userId", user.getId());
            TermQueryBuilder parentComentId = QueryBuilders.termQuery("parentComentId.keyword", commentId);
            boolQueryBuilder.must(userId).must(parentComentId);
            NativeSearchQuery nativeSearchQuery = esTemplate.genNativeSearchQuery(boolQueryBuilder);
            SearchHits<Like> search = esTemplate.search(nativeSearchQuery, Like.class, IndexName.Like);
            if (search.getTotalHits() != 0) {
                // 一个用户对一个评论只能是赞或者不赞，所以这里如果有值的话 也只会有一条，可以直接去第一条是数据
                List<SearchHit<Like>> searchHits = search.getSearchHits();
                String id = searchHits.get(0).getContent().getId();
                esTemplate.delDocument(id, IndexName.Like);
            } else {
                Like like = new Like();
                like.setUserId(user.getId());
                like.setParentComentId(commentId);
                like.setCreateTime(TimeUtil.ParseDate(new Date(), 1));
                like.setTextId(textId);
                esTemplate.save(like, IndexName.Like);
            }
        } else {
            Like like = new Like();
            like.setUserId(user.getId());
            like.setParentComentId(commentId);
            like.setCreateTime(TimeUtil.ParseDate(new Date(), 1));
            like.setTextId(textId);
            esTemplate.save(like, IndexName.Like);
        }


    }


}

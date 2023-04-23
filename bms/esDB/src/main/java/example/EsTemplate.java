package example;


import example.Pojo.log;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class EsTemplate {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    public void save(Object data) {
        elasticsearchRestTemplate.save(data, IndexCoordinates.of("log"));
    }


    public void save(Object data, String indexName) {
        elasticsearchRestTemplate.save(data, IndexCoordinates.of(indexName));
    }


    public SearchHits<log> getData() {
        return elasticsearchRestTemplate.search(Query.findAll(), log.class);
    }

    /**
     * 检查索引库是否存在
     *
     * @param indexName
     * @return
     */
    public boolean indexInclude(String indexName) {
        return elasticsearchRestTemplate.indexExists(indexName);
    }


    /**
     * 根据索引库的名称 直接删除该索引库
     *
     * @param indexName
     * @return
     */
    public boolean delIndex(String indexName) {
        return elasticsearchRestTemplate.deleteIndex(indexName);
    }

    /**
     * 删除该索引的 id数据
     *
     * @param id
     * @param indexName
     * @return
     */
    public boolean delDocument(Integer id, String indexName) {
        String delete = elasticsearchRestTemplate.delete(id, IndexCoordinates.of(indexName));
        return delete.equals("ok");
    }

    /**
     * 删除该索引的 id数据
     *
     * @param id
     * @param indexName
     * @return
     */
    public boolean delDocument(String id, String indexName) {
        String delete = elasticsearchRestTemplate.delete(id, IndexCoordinates.of(indexName));
        return delete.equals("");
    }


    /**
     * 查询
     * @param query 查询条件
     * @param res 返回的类
     * @param indexName 查询的索引库
     * @return
     * @param <T>
     */
    public <T> SearchHits<T> search(Query query, Class<T> res, String indexName) {
        return elasticsearchRestTemplate.search(query, res, IndexCoordinates.of(indexName));
    }



    public RangeQueryBuilder genRangeQuery(String fieldName,Object start,Object end) {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(fieldName);
        rangeQueryBuilder.gte(start);
        rangeQueryBuilder.lte(end);

        return rangeQueryBuilder;
    }


    public RangeQueryBuilder genRangeQuery(String fieldName,Object start) {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(fieldName);
        rangeQueryBuilder.gte(start);

        return rangeQueryBuilder;
    }


    public Pageable page(Integer currPage,Integer perPage) {
        currPage -= 1;
        perPage -= 1;
        return PageRequest.of(currPage, perPage);
    }


    /**
     *
     * @param QueryBuilder 构建的es查询条件，根据这个条件来查询对应的数据
     * @return
     */
    public NativeSearchQuery genNativeSearchQuery(QueryBuilder QueryBuilder,Pageable pageable) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withTrackScores(true);
        nativeSearchQueryBuilder.withQuery(QueryBuilder);
        nativeSearchQueryBuilder.withPageable(pageable);



        return nativeSearchQueryBuilder.build();
    }


    public NativeSearchQuery genNativeSearchQuery(QueryBuilder QueryBuilder) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withTrackScores(true);
        nativeSearchQueryBuilder.withQuery(QueryBuilder);


        return nativeSearchQueryBuilder.build();
    }


    public NativeSearchQuery genNativeSearchQuery(QueryBuilder QueryBuilder, SortBuilder sort) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withTrackScores(true);
        nativeSearchQueryBuilder.withQuery(QueryBuilder);

        nativeSearchQueryBuilder.withSort(sort);

        return nativeSearchQueryBuilder.build();
    }


    public NativeSearchQueryBuilder genNativeSearchQuery() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withTrackScores(true);

        return nativeSearchQueryBuilder;
    }



}

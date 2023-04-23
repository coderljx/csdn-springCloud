package example;

import example.Pojo.Comment;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsDBr {

    @Resource
    private EsTemplate esTemplate;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void a1() {
        Comment comment = new Comment();
        comment.setComment("回复31用户的发言");
        comment.setUserId(32);
        comment.setCreateTime(new Date().getTime());
        comment.setTextId(9);

        comment.setParentComentId("2hoNhYcB4_Tgmc_AcLUu");

        esTemplate.save(comment,"comment");
    }



    @Test
    public void a() {

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();


        nativeSearchQueryBuilder.addAggregation(new TermsAggregationBuilder("test", ValueType.STRING).field("userId"));



        SearchHits<Comment> search = esTemplate.search(nativeSearchQueryBuilder.build(),
                Comment.class, "comment");

        System.out.println(11);
        
        for (SearchHit<Comment> commentSearchHit : search) {
            String id = commentSearchHit.getContent().getId();
        }



    }






}

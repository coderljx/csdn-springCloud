package example.Pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 记录用户给那些评论点过赞
 */
@Data
public class Like {
    @Field(type = FieldType.Text)
    private String id;

    /**
     * 评论的用户id keyword 类型，让es不分词 直接查询
     */
    @Field(type = FieldType.Keyword)
    private Integer userId;

    @Field(type = FieldType.Text)
    private String createTime;

    @Field(type = FieldType.Integer)
    private Integer textId; // 该评论信息是那个文章下面的

    /**
     * 该评论是否为回复评论
     * 该字段为空 ，表示当前评论为顶层评论
     */
    @Field(type = FieldType.Keyword)
    private String parentComentId;

}

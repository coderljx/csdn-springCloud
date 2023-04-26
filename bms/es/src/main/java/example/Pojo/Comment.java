package example.Pojo;


import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Data
public class Comment {

    @Field(type = FieldType.Text)
    private String id;

    /**
     * 评论的用户id keyword 类型，让es不分词 直接查询
     */
    @Field(type = FieldType.Keyword)
    private Integer userId;

    @Field(type = FieldType.Text)
    private String userName;

    @Field(type = FieldType.Text)
    private String comment;

    @Field(type = FieldType.Text)
    private String createTime;

    @Field(type = FieldType.Integer)
    private Integer textId; // 该评论信息是那个文章下面的

    // 楼层id， 通过楼层id 可以区分那些评论是该楼层下的
    @Field(type = FieldType.Text)
    private String foor;

    /**
     * 该评论是否为回复评论
     * 该字段为空 ，表示当前评论为顶层评论
     */
    @Field(type = FieldType.Keyword)
    private String parentComentId;

    @Field(type = FieldType.Integer)
    private Integer isDelete;





}

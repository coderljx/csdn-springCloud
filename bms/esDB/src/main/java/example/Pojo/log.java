package example.Pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "log", createIndex = false)
@Data
public class log {
    @Field
    private String id;
    @Field
    private String url;
    @Field
    private String dec;
    @Field
    private String params;
    @Field
    private String resparams;

}

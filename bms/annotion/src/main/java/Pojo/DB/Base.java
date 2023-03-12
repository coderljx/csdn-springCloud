package Pojo.DB;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Base {
    private Integer id;
    private String createTime;
    private String createBy;
    private String modifyTime;
    private String modifyBy;
}

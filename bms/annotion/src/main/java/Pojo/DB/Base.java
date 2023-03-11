package Pojo.DB;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Base {
    private Integer id;
    private Date createTime;
    private String createBy;
    private Date modifyTime;
    private String modifyBy;
}

package Pojo.DB;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Base {
    private Integer id;
    private Date create_time;
    private String create_by;
    private Date modify_time;
    private String modify_by;
}

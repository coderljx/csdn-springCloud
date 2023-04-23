package Pojo.DB;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 所有自定义 vo类都必须继承该类
 * 用于 ： 将 null值字段从返回json中删除
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL,content = JsonInclude.Include.NON_DEFAULT)
public class BeanVo {
}

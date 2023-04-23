package coderljxTitle.Bean;

import Pojo.DB.BeanVo;
import lombok.Data;

@Data
public class UserVo extends BeanVo {

    private Integer userId; // 用户id

    private String userName;

    private Integer createTime; // 用户创建时间与当前时间的差值（单位： 天数）

    private Integer viewCount; // 当前这个用户id下的所有文章的 view（被查看次数）字段总和


    private Integer originalityCount; // 当前用户的所有文章总量（没有被删除的）

    private Integer isFollow; // 如果当前用户登录了，那么会查询当前用户是否有关注过跟文章的作者吗？




}

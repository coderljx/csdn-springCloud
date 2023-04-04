package Activities.Dao;

import Pojo.DB.Activities;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivitiesDao {

    /**
     * 获取所有的活动信息
     * @param limit
     * @param offset
     * @return
     */
    List<Activities> getAll(@Param("limit") Integer limit,
                            @Param("offset") Integer offset);


    /**
     * 新增一个活动
     * @param activities
     * @param createBy
     * @return
     */
    Integer addActivice(@Param("list") List<Activities> activities,
                        @Param("createBy") String createBy);


    Integer updateActivice(@Param("activities") Activities activities,
                           @Param("createBy") String createBy);
}
